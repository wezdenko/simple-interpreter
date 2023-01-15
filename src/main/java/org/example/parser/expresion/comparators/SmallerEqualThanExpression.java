package org.example.parser.expresion.comparators;

import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public class SmallerEqualThanExpression extends ComparisonExpression {

    public SmallerEqualThanExpression(Identifier identifier, Value value) {
        super(identifier, value);
    }
}
