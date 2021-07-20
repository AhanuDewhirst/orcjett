package com.orcjett.api.base.io;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SystemIO {
    private SystemIO() {}

    public static void watch(Path directory, WatchServiceListener listener) throws IOException {
        if (Files.isDirectory(directory, LinkOption.NOFOLLOW_LINKS)) {
            var service = FileSystems.getDefault().newWatchService();
            directory.register(service,
                    WatchServiceListener.ENTRY_CREATE,
                    WatchServiceListener.ENTRY_DELETE,
                    WatchServiceListener.ENTRY_MODIFY
            );

            WATCH_EXECUTOR.execute(() -> {
                WatchKey key;

                try {
                    while ((key = service.take()) != null) {
                        for (var event : key.pollEvents()) {
                            listener.onWatchEvent((Path) event.context(), event.kind());
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static final ExecutorService WATCH_EXECUTOR =
            Executors.newCachedThreadPool(r -> {
                var thread = new Thread("WatchService");
                thread.setDaemon(true);
                return thread;
            });
}
