package org.example.parser.expresion.comparators;

import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public class GreaterEqualThanExpression extends ComparisonExpression {

    public GreaterEqualThanExpression(Identifier identifier, Value value) {
        super(identifier, value);
    }
}
