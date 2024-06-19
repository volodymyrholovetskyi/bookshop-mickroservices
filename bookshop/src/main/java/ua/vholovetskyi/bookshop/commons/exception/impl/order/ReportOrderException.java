package ua.vholovetskyi.bookshop.commons.exception.impl.order;

import ua.vholovetskyi.bookshop.commons.exception.BusinessException;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
public class ReportOrderException extends BusinessException {
    public ReportOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
