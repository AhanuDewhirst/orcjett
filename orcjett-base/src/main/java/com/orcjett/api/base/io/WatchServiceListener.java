package com.orcjett.api.base.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

@FunctionalInterface
public interface WatchServiceListener {
    WatchEvent.Kind<?> ENTRY_CREATE = StandardWatchEventKinds.ENTRY_CREATE;
    WatchEvent.Kind<?> ENTRY_DELETE = StandardWatchEventKinds.ENTRY_DELETE;
    WatchEvent.Kind<?> ENTRY_MODIFY = StandardWatchEventKinds.ENTRY_MODIFY;

    void onWatchEvent(Path path, WatchEvent.Kind<?> kind) throws IOException;
}
