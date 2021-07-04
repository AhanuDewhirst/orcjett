package com.orcjett.api.base.event;

/**
 * An occurrence in the application. Event is the common superinterface for all events.
 * When an event occurs, an instance of Event is published to all subscribers of an {@link EventBus EventBus}.
 */
public interface Event {
    /**
     * Returns the source of the event.
     * @return the source of the event
     */
    Object getSource();
}