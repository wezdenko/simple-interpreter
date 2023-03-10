package org.example.parser.expresion.booleans;

import org.example.interpreter.interfaces.IVisitor;
import org.example.parser.expresion.IExpression;

public class AndExpression implements IExpression {

    private final IExpression left;
    private final IExpression right;

    public AndExpression(IExpression left, IExpression right) {
        this.left = left;
        this.right = right;
    }

    public IExpression getLeft() {
        return left;
    }

    public IExpression getRight() {
        return right;
    }

    @Override
    public boolean accept(IVisitor visitor) {
        return visitor.visit(this);
    }
}
