package org.project.streamingapi.patterns.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggingService {

    private final Logger log = LoggerFactory.getLogger("MovieStreaming");

    private LoggingService() { }

    private static class Holder {
        private static final LoggingService INSTANCE = new LoggingService();
    }

    public static LoggingService getInstance() {
        return Holder.INSTANCE;
    }

    public void info(String message) {
        log.info(message);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void error(String message, Throwable t) {
        log.error(message, t);
    }
}

