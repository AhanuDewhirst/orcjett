package com.orcjett.api.base.application;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Address represents a uniquely identifiable resource or object.
 */
public final class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = -285006507232430766L;

    private final String identity;
    private final String namespace;

    private Address(String namespace, String identity) {
        if ((namespace == null) || namespace.isBlank()) {
            throw new MalformedAddressException("Namespace is undefined");
        }

        if ((identity == null) || identity.isBlank()) {
            throw new MalformedAddressException("Identity is undefined");
        }
        this.namespace = namespace;
        this.identity = identity;
    }

    /**
     * Returns the identity of this address.
     * @return identity of this address.
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Returns the namespace of this address.
     * @return namespace of this address.
     */
    public String getNamespace() {
        return namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return identity.equals(address.identity) && namespace.equals(address.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity, namespace);
    }

    @Override
    public String toString() {
        return namespace + ":" + identity;
    }

    /**
     * Constructs a new Address with the specified namespace and identity.
     *
     * @param namespace address namespace
     * @param identity address identity
     * @return new address object
     */
    public static Address of(String namespace, String identity) {
        return new Address(namespace, identity);
    }
}
