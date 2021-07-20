package com.orcjett.api.base.application;

import java.io.Serial;

/**
 * Thrown to indicate that a malformed {@link Address Address} has occurred.
 */
public final class MalformedAddressException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7995856520584074357L;

    public MalformedAddressException() {
        super();
    }

    public MalformedAddressException(String message) {
        super(message);
    }
}
