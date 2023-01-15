package org.example.parser.expresion.literals;

import org.example.parser.expresion.IExpression;

public class Value implements IExpression {

    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
