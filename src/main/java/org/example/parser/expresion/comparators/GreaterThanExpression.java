package org.example.parser.expresion.comparators;

import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public class GreaterThanExpression extends ComparisonExpression {

    public GreaterThanExpression(Identifier identifier, Value value) {
        super(identifier, value);
    }
}
