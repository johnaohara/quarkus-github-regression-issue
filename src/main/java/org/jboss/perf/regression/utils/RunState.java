package org.jboss.perf.regression.utils;

public enum RunState {
    SUCCESS("success"),
    FAILED("failed"),
    CANCELLED("cancelled");

    String state;

    RunState(String state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return state;
    }


}
