package org.wso2.ballerina;

public class Rule {
    private final String id;
    private final String description;

    public Rule(int id, String description) {
        this.id = "C" + id;
        this.description = description;
    }
}
