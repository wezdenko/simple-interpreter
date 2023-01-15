package org.example.parser.expresion.comparators;

import org.example.parser.expresion.IExpression;
import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public abstract class ComparisonExpression implements IExpression {

    protected final Identifier identifier;
    protected final Value value;

    public ComparisonExpression(Identifier identifier, Value value) {
        this.identifier = identifier;
        this.value = value;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Value getValue() {
        return value;
    }
}
