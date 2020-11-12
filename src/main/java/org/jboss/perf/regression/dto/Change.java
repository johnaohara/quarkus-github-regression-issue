package org.jboss.perf.regression.dto;

import java.time.Instant;

public class Change {

    public int id;

    public Variable variable;

    public int runId;

    public Instant timestamp;

    public boolean confirmed;

    public String description;
}
