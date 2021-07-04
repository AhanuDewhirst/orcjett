package com.orcjett.api.base.event;

import java.lang.reflect.Type;

/**
 * A parameterised event. GenericEvent is an interface for defining parameterised events to be published on an
 * {@link EventBus EventBus}.
 * @param <T> event-specific parameter type
 */
public interface GenericEvent<T> extends Event {
    /**
     * Returns the generic type of the event.
     * @return the generic type of the event
     */
    Type getGenericType();
}