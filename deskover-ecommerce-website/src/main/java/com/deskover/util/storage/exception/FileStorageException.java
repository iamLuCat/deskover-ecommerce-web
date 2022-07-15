package com.deskover.util.storage.exception;

public class FileStorageException extends RuntimeException {
    private static final long serialVersionUID = -8390469680780898591L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}