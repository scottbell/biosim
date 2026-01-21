package com.traclabs.biosim.server.util.tickwriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class SimIdProvider {
    private static final AtomicInteger counter = new AtomicInteger();

    /**
     * Returns the next simulation ID.
     * If writeTicks == true, pick ID based on logs/sim_<n> directories.
     * Otherwise use an in-memory counter.
     *
     * @param logRoot the root directory where simulation log directories are stored.
     * @param writeTicks whether tick logging is enabled.
     * @return the next simulation ID.
     * @throws IOException if an I/O error occurs while reading the directory.
     */
    public static int nextId(Path logRoot, boolean writeTicks) throws IOException {
        if (writeTicks) {
            int id = nextSimId(logRoot);      // existing scan method
            counter.set(id);                 // keep counter in sync
            return id;
        }
        return counter.incrementAndGet();
    }

    /**
     * Scans the given logRoot directory for directories named "sim_<n>" and returns the next available simulation ID.
     * If no such directory exists, returns 0.
     *
     * @param logRoot the root directory where simulation log directories are stored.
     * @return the next simulation ID.
     * @throws IOException if an I/O error occurs while reading the directory.
     */
    private static int nextSimId(Path logRoot) throws IOException {
        if (!Files.exists(logRoot)) {
            Files.createDirectories(logRoot);
            return 1;
        }
        try (Stream<Path> stream = Files.list(logRoot)) {
            Optional<Integer> maxId = stream
                .filter(Files::isDirectory)
                .map(path -> {
                    String name = path.getFileName().toString();
                    if (name.startsWith("sim_")) {
                        try {
                            return Integer.parseInt(name.substring(4));
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                    return null;
                })
                .filter(id -> id != null)
                .max(Comparator.naturalOrder());
            return maxId.isPresent() ? maxId.get() + 1 : 0;
        }
    }
}
