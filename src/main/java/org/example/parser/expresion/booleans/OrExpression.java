package org.example.parser.expresion.booleans;

import org.example.parser.expresion.IExpression;

public class OrExpression implements IExpression {

    private final IExpression left;
    private final IExpression right;

    public OrExpression(IExpression left, IExpression right) {
        this.left = left;
        this.right = right;
    }

    public IExpression getLeft() {
        return left;
    }

    public IExpression getRight() {
        return right;
    }
}
