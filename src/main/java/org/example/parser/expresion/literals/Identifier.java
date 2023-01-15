package org.example.parser.expresion.literals;

import org.example.interpreter.interfaces.ILiteralVisitable;
import org.example.interpreter.interfaces.IVisitor;

public class Identifier implements ILiteralVisitable {

    private final String value;

    public Identifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
