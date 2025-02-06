package com.app.backend.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.backend.dto.*;
import com.app.backend.exception.UserNotFoundException;
import com.app.backend.model.User;
import com.app.backend.responses.LoginResponse;
import com.app.backend.service.AuthenticationService;
import com.app.backend.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("")
    public ResponseEntity<String> hello()  {
        return ResponseEntity.ok("Bienvenue sur listify");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.signup(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Compte créé avec succès");
    }

    @PostMapping("/register/superuser")
    public ResponseEntity<?> registerSuperUser(@RequestBody RegisterSuperUserDto registerSuperUserDto) {
        try {
            authenticationService.signupSuperUser(registerSuperUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Superutilisateur créé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User user = authenticationService.authenticate(loginUserDto);
        String jwttoken = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse(user.getName(), user.getSurname(), user.getEmail(), jwttoken,
                jwtService.getExpirationTime(), user.getRoles(), false);
        
        // Vérification du rôle de superutilisateur
        if (user.isSuperuser()) {
            loginResponse.setRedirectUrl("/admin");
            loginResponse.setIs_superuser(true);
        } else {
            loginResponse.setRedirectUrl("/user");
        }

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateUser(@RequestHeader("Authorization") String token) {
        try {
            String userEmail = authenticationService.validateUser(token);

            return ResponseEntity.ok("Utilisateur authentifié : " + userEmail);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Compte vérifié avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationToken(@RequestBody ResendVerificationDto resendVerificationDto) {
        try {
            authenticationService.resendVerificationToken(resendVerificationDto.getEmail());
            return ResponseEntity.ok(
                    "Code de verification envoyé, veuillez consulter votre boite mail, si pas present vérifie les spams");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        try {
            authenticationService.forgotPassword(forgotPasswordDto.getEmail());
            return ResponseEntity.ok("Un email pour réinitialiser le mot de passe a été envoyé.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur survenue lors de l'envoi de l'email.");
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            authenticationService.resetPassword(resetPasswordDto.getToken(), resetPasswordDto.getNewPassword());
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
