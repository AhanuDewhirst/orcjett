package com.orcjett.api.base.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class Namespace {
    private final String namespace;
    private final ClassLoader classLoader;
    private final Configuration configuration;
    private final Logger logger;

    protected Namespace(String namespace) {
        this.namespace = namespace;
        this.classLoader = super.getClass().getClassLoader();
        this.configuration = new Configuration(this);
        this.logger = LogManager.getLogger(namespace);
    }

    public final String getNamespace() {
        return namespace;
    }

    public final Configuration getConfiguration() {
        return configuration;
    }

    public final Address address(String identity) {
        return Address.of(namespace, identity);
    }

    public final String getLocalizedString(String identity) {
        return getLocalizedString(identity, Locale.getDefault());
    }

    public final String getLocalizedString(String identity, Locale locale) {
        var bundle = ResourceBundle.getBundle("locale." + namespace, locale, classLoader);

        try {
            return bundle.getString(identity);
        } catch (MissingResourceException e) {
            return identity;
        }
    }

    public final boolean resourceExists(String identity) {
        return getResourceAsStream(identity) != null;
    }

    public final URL getResource(String identity) {
        return classLoader.getResource(identity);
    }

    public final InputStream getResourceAsStream(String identity) {
        return classLoader.getResourceAsStream(identity);
    }

    public final BufferedReader getResourceAsReader(String identity) {
        return new BufferedReader(new InputStreamReader(getResourceAsStream(identity)));
    }

    Logger getLogger() {
        return logger;
    }

    public final void logDebug(String message, Object... params) {
        logger.debug(message, params);
    }

    public final void logInfo(String message, Object... params) {
        logger.info(message, params);
    }

    public final void logWarn(String message, Object... params) {
        logger.warn(message, params);
    }

    public final void logError(String message, Object... params) {
        logger.error(message, params);
    }

    public final void logFatal(String message, Object... params) {
        logger.fatal(message, params);
    }

    public final void logCatching(Throwable throwable) {
        logger.catching(throwable);
    }

    public final <T extends Throwable> T logThrowing(T throwable) {
        return logger.throwing(throwable);
    }
}
