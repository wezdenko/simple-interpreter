package org.example.parser.expresion.literals;

import org.example.parser.expresion.IExpression;

public class Identifier implements IExpression {

    private final String value;

    public Identifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
