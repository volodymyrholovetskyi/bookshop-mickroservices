package ua.vholovetskyi.bookshop.commons.exception;

import java.time.Instant;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-20
 */
public record ErrorResponse(String status, int statusCode, String message, Instant timestamp) {

}
