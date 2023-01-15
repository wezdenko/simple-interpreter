package org.example.parser.expresion.comparators;

import org.example.interpreter.interfaces.IVisitor;
import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public class EqualExpression extends ComparisonExpression {

    public EqualExpression(Identifier identifier, Value value) {
        super(identifier, value);
    }

    @Override
    public boolean accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
