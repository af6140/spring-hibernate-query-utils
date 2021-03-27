package com.yannbriancon.config;


import java.io.Serializable;

public class NPlusOneQueriesDetectionProperties implements Serializable {
    public enum ErrorLevel {
        INFO,
        WARN,
        ERROR,
        EXCEPTION
    }

    /**
     * Error level for the N+1 queries detection
     */
    private ErrorLevel errorLevel = ErrorLevel.valueOf("ERROR");

    /**
     * Boolean allowing to enable or disable the N+1 queries detection
     */
    private boolean enabled = true;

    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(ErrorLevel errorLevel) {
        this.errorLevel = errorLevel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
