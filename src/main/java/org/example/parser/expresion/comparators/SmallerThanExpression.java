package org.example.parser.expresion.comparators;

import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public class SmallerThanExpression extends ComparisonExpression {

    public SmallerThanExpression(Identifier identifier, Value value) {
        super(identifier, value);
    }
}
