package org.example.parser.expresion;

import org.example.interpreter.interfaces.IVisitor;

public class TopExpression implements IExpression {

    private final IExpression expression;

    public TopExpression(IExpression expression) {
        this.expression = expression;
    }

    public IExpression getExpression() {
        return expression;
    }

    @Override
    public boolean accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
