package com.app.backend.enums;

public enum ErrorMessages {
    EMAIL_ALREADY_USED("Cet email est déjà utilisé"),
    TOKEN_EXPIRED("Le token de vérification a expiré."),
    TOKEN_INVALID("Le token de vérification est invalide."),
    USER_NOT_FOUND("Utilisateur non trouvé"),
    USER_REGISTRATION_FAILED("Impossible d'enregistrer l'utilisateur"),
    USER_ALREADY_VERIFIED("L'utilisateur a déjà été vérifié"),
    EMAIL_NOT_SENT("Impossible d'envoyer l'email");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
