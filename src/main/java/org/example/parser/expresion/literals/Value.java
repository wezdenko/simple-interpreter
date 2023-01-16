package org.example.parser.expresion.literals;

import com.fasterxml.jackson.annotation.JsonValue;
import org.example.interpreter.interfaces.ILiteralVisitable;
import org.example.interpreter.interfaces.IVisitor;

public class Value implements ILiteralVisitable {

    @JsonValue
    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
