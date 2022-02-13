package ru.mrak.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "ru";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String ID_GENERATOR = "ID_GENERATOR";

    // Количество предложения до и после изучаемого предложения
    public static final int SENTENCES_BEFORE = 2;
    public static final int SENTENCES_AFTER = 2;

    private Constants() {
    }
}
