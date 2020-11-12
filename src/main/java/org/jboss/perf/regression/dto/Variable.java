package org.jboss.perf.regression.dto;

public class Variable {
    public Integer id;

    public int testId;

    public String name;

    public String group;

    public int order;

    public String accessors;

    public String calculation;

    public double maxDifferenceLastDatapoint = 0.2;

    public int minWindow = 5;

    public double maxDifferenceFloatingWindow = 0.1;

    public int floatingWindow = 7;

}
