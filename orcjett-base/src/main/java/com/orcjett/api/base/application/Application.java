package com.orcjett.api.base.application;

import com.orcjett.api.base.event.EventBus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Application extends Namespace {
    private final EventBus eventBus;

    public Application(String namespace) {
        super(namespace);
        setupSystemOutput();
        setupShutdownHook();

        this.eventBus = new EventBus();
    }

    public final EventBus getEventBus() {
        return eventBus;
    }

    public final Path getHomeDirectory() {
        var directory = Path.of(System.getProperty("user.home") + "/" + super.getNamespace());

        if (!Files.isDirectory(directory)) {
            try {
                Files.createDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory;
    }

    public final Path createDirectory(String path) {
        return getHomeDirectory().resolve(path);
    }

    public final void launch(String[] args) {
        var configuration = super.getConfiguration();
        init(configuration);
        start(configuration);
    }

    private void setupSystemOutput() {
        System.setOut(IoBuilder.forLogger(super.getLogger()).setLevel(Level.INFO).buildPrintStream());
        System.setErr(IoBuilder.forLogger(super.getLogger()).setLevel(Level.ERROR).buildPrintStream());
    }

    private void setupShutdownHook() {
        var shutdownHook = new Thread(() -> shutdown(super.getConfiguration()), "Shutdown-Hook");
        shutdownHook.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    protected abstract void init(Configuration configuration);
    protected abstract void start(Configuration configuration);
    protected abstract void shutdown(Configuration configuration);
}
