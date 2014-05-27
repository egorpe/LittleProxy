package org.littleshoot.proxy.impl;

import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.Logger;

/**
 * <p>
 * A helper class that logs messages for ProxyConnections. All it does is make
 * sure that the Channel and current state are always included in the log
 * messages (if available).
 * </p>
 * 
 * <p>
 * Using LocationAwareLogger is WRONG WRONG WRONG so I got rid of it.
 * </p>
 */
class ProxyConnectionLogger {
    private final ProxyConnection connection;
    private final Logger logger;

    public ProxyConnectionLogger(ProxyConnection connection) {
        this.connection = connection;
        this.logger = LoggerFactory.getLogger(connection.getClass());
    }

    protected void error(String message, Object... params) {
        if (logger.isErrorEnabled()) {
            logger.error(formattedMesage(message, params));
        }
    }

    protected void error(String message, Throwable t) {
        if (logger.isErrorEnabled()) {
            logger.error(formattedMesage(message, null), t);
        }
    }

    protected void warn(String message, Object... params) {
        if (logger.isWarnEnabled()) {
            logger.warn(formattedMesage(message, params));
        }
    }

    protected void warn(String message, Throwable t) {
        if (logger.isWarnEnabled()) {
            logger.warn(formattedMesage(message, null), t);
        }
    }

    protected void info(String message, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info(formattedMesage(message, params));
        }
    }

    protected void info(String message, Throwable t) {
        if (logger.isInfoEnabled()) {
            logger.info(formattedMesage(message, null), t);
        }
    }

    protected void debug(String message, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug(formattedMesage(message, params));
        }
    }

    protected void debug(String message, Throwable t) {
        if (logger.isDebugEnabled()) {
            logger.debug(formattedMesage(message, null), t);
        }
    }

    private String formattedMesage(String message, Object[] params) {
        String formattedMessage = fullMessage(message);
        if (params != null && params.length > 0) {
            formattedMessage = MessageFormatter.arrayFormat(formattedMessage, params).getMessage();
        }
        return formattedMessage;
    }

    private String fullMessage(String message) {
        String stateMessage = connection.getCurrentState().toString();
        if (connection.isTunneling()) {
            stateMessage += " {tunneling}";
        }
        String messagePrefix = "(" + stateMessage + ")";
        if (connection.channel != null) {
            messagePrefix = messagePrefix + " " + connection.channel;
        }
        return messagePrefix + ": " + message;
    }
}