package ua.vholovetskyi.bookshop.commons.exception.impl.order;

import ua.vholovetskyi.bookshop.commons.exception.BusinessException;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
public class UploadOrderException extends BusinessException {
    public UploadOrderException(Throwable cause) {
        super("An error occurred while uploading the file!", cause);
    }
}
