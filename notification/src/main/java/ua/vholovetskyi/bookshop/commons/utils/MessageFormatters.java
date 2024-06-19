package ua.vholovetskyi.bookshop.commons.utils;

public final class MessageFormatters {

    private MessageFormatters() {
        throw new UnsupportedOperationException("Cannot instantiate MessageFormatter");
    }

    public static final String EMPTY_MSG = null;
    public static final String ERROR_MSG = "Exception class: %s. Message: %s";


    public static String formatMessage(Exception e) {
        return ERROR_MSG.formatted(e.getClass().getSimpleName(), e.getMessage());
    }
}
