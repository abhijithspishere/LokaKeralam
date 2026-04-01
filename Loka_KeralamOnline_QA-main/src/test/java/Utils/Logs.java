package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logs {
    private final Logger logger;

    public Logs(String className) {
        this.logger = LogManager.getLogger(className);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Object... params) {
        logger.info(message, params);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Object... params) {
        logger.error(message, params);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void debug(String message, Object... params) {
        logger.debug(message, params);
    }
}