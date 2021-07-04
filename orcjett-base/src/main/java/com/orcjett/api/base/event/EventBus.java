package com.orcjett.api.base.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Mechanism for coordinating centralised publish-subscribe communication.
 */
public final class EventBus {
    private final List<Object> subscribers;

    /**
     * Constructs a new EventBus object.
     */
    public EventBus() {
        this.subscribers = Collections.synchronizedList(new ArrayList<>());
        subscribers.add(null);
    }

    /**
     * Subscribes the specified object to this event bus.
     * @param subscriber object to subscribe
     */
    public void subscribe(Object subscriber) {
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    /**
     * Unsubscribes the specified object from this event bus.
     * @param subscriber object to unsubscribe
     */
    public void unsubscribe(Object subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * Publish an event to all subscribers of this event bus.
     * @param event event to publish
     */
    public void publish(Event event) {
        synchronized (subscribers) {
            subscribers.forEach(subscriber -> {
                var methods = getMethodsForEvent(event, subscriber);
                methods.forEach(method -> invokeEventMethod(method, event, subscriber));
            });
        }
    }

    /**
     * Retrieves a list of methods from the specified subscriber that match the event as a parameter.
     *
     * @param event event to match
     * @param subscriber subscriber to search
     * @return corresponding methods for the event
     */
    private List<Method> getMethodsForEvent(Event event, Object subscriber) {
        var result = new ArrayList<Method>();

        Arrays.stream(subscriber.getClass().getDeclaredMethods()).forEach(method -> {
            var annotation = method.getDeclaredAnnotation(Subscribe.class);

            if (annotation == null) {
                return;
            }
            var parameterTypes = method.getParameterTypes();

            // Method must have one parameter whose type matches the event.
            if ((parameterTypes.length == 1) && (parameterTypes[0].isInstance(event))) {
                if (event instanceof GenericEvent) {
                    var eventTypeName = ((GenericEvent<?>) event).getGenericType().getTypeName();
                    var methodTypeName = method.getGenericParameterTypes()[0].getTypeName();

                    if (!methodTypeName.contains(eventTypeName)) {
                        return;
                    }
                }
                method.setAccessible(true);
                result.add(method);
            }
        });
        return result;
    }

    /**
     * Invokes the specified method for the subscriber, passing the event as a parameter.
     *
     * @param method method to invoke
     * @param event event to pass as a parameter
     * @param subscriber subscriber to receive the event
     */
    private void invokeEventMethod(Method method, Event event, Object subscriber) {
        try {
            method.invoke(subscriber, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
