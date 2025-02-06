package com.app.backend.service;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.dto.LoginUserDto;
import com.app.backend.dto.RegisterSuperUserDto;
import com.app.backend.dto.RegisterUserDto;
import com.app.backend.dto.VerifyUserDto;
import com.app.backend.enums.ErrorMessages;
import com.app.backend.exception.UserAlreadyVerifiedException;
import com.app.backend.exception.UserNotFoundException;
import com.app.backend.model.User;
import com.app.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Set;


@Service
public class AuthenticationService {

    @Value("${app.frontend.url}")
    private static String frontendUrl;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public void signup(RegisterUserDto input) {
        logger.info("Tentative d'inscription avec l'e-mail : {}", input.getEmail());
        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());
        if (existingUser.isPresent()) {
            logger.warn("Échec de l'inscription : l'e-mail {} est déjà utilisé.", input.getEmail());
            throw new IllegalArgumentException("L'adresse email a déja été utilisé.");
        }
        User user = new User(
            input.getName(),
            input.getSurname(),
            input.getEmail(),
            passwordEncoder.encode(input.getPassword()));
            
            user.setEmailVerificationToken(generateVerificationToken());
            user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusMinutes(15));
            user.setEnabled(true);
            
            // Envoi de l'email de vérification
            // sendVerificationEmail(user);
            
            userRepository.save(user);
            logger.info("Utilisateur enregistré et e-mail de vérification envoyé pour {}", input.getEmail());
    }

    public void signupSuperUser(RegisterSuperUserDto registerSuperUserDto) {
        
        User user = new User();
        user.setName(registerSuperUserDto.getName());
        user.setSurname(registerSuperUserDto.getSurname());
        user.setEmail(registerSuperUserDto.getEmail());
        user.setPassword(registerSuperUserDto.getPassword());
        user.setSuperuser(true);

        
        userRepository.save(user);
    }


    public User authenticate(LoginUserDto input) {
        logger.info("Tentative d'authentification pour l'e-mail : {}", input.getEmail());
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> {
                    logger.error("L'utilisateur avec l'e-mail {} n'a pas été trouvé.", input.getEmail());
                    return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
                });
        if (!user.isEnabled()) {
            logger.warn("Échec d'authentification : Compte non vérifié pour l'e-mail {}", input.getEmail());
            throw new RuntimeException("Compte non vérifié. Veuillez verifier votre compte.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));
        logger.info("Utilisateur authentifié avec succès : {}", input.getEmail());
        return user;
    }

    public void verifyUser(VerifyUserDto input) {

        Optional<User> userOptional = userRepository.findByEmail(input.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getEmailVerificationTokenExpiry() == null) {
                throw new RuntimeException("Le token n'a pas de date d'expiration définie.");
            }
            if (user.getEmailVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new RuntimeException(ErrorMessages.TOKEN_EXPIRED.getMessage());
            }
            if (user.getEmailVerificationToken().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setEmailVerificationToken(null);
                user.setEmailVerificationTokenExpiry(null);
                userRepository.save(user);
                String welcomeMessage = "🎉 Bienvenue sur BookNest, " + user.getName() + " ! Nous sommes ravis de vous compter parmi nous.";
            } else {
                throw new RuntimeException("Token invalide");
            }
        } else {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
    }

    public String validateUser(String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        String userEmail = jwtService.extractUsername(jwtToken);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage()));

        if (!jwtService.isTokenValid(jwtToken, user)) {
            throw new RuntimeException("Token invalide ou expiré");
        }
        return userEmail;
    }

    public void resendVerificationToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isEnabled()) {
                throw new UserAlreadyVerifiedException(ErrorMessages.USER_ALREADY_VERIFIED.getMessage());
            }
            user.setEmailVerificationToken(generateVerificationToken());
            user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusHours(1));

            sendVerificationEmail(user);

            userRepository.save(user);
        } else {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
    }

    public void sendVerificationEmail(User user) {
        String subject = "Vérification de compte";
        String verificationToken = user.getEmailVerificationToken();

        String textMessage = "Bienvenue sur notre application!\n\n"
                + "S'il vous plaît entrer le code de vérification ci-dessous pour continuer:\n\n"
                + "Code de vérification: " + verificationToken + "\n\n"
                + "Merci!";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, textMessage);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(ErrorMessages.EMAIL_NOT_SENT.getMessage());
        }

    }

    private String generateVerificationToken() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable avec cet email : " + email));

        // Générer un token de réinitialisation
        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        // Préparer le message et envoyer l'email
        String subject = "Réinitialisation de votre mot de passe";
        String message = getMessage(resetToken);

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email de réinitialisation de mot de passe.");
        }
    }

    private static String getMessage(String resetToken) {
        String resetLink = frontendUrl + "/reset-password?token=" + resetToken;

        return "Réinitialisation de votre mot de passe\n\n"
                + "Pour réinitialiser votre mot de passe, cliquez sur le lien ci-dessous :\n"
                + resetLink + "\n\n"
                + "Ce lien expirera dans une heure.";
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token de réinitialisation invalide ou expiré."));

        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de réinitialisation expiré.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);

        userRepository.save(user);
    }
}
