package org.example.interpreter;

import org.example.interpreter.interfaces.IVisitor;
import org.example.parser.expresion.TopExpression;
import org.example.parser.expresion.booleans.AndExpression;
import org.example.parser.expresion.booleans.OrExpression;
import org.example.parser.expresion.comparators.*;
import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

import java.util.Map;

public class InterpreterVisitor implements IVisitor {

    private final Map<Character, Integer> records;

    public InterpreterVisitor(Map<Character, Integer> records) {
        this.records = records;
    }

    @Override
    public boolean visit(TopExpression element) {
        return element.getExpression().accept(this);
    }

    @Override
    public boolean visit(OrExpression element) {
        return element.getLeft().accept(this) || element.getRight().accept(this);
    }

    @Override
    public boolean visit(AndExpression element) {
        return element.getLeft().accept(this) && element.getRight().accept(this);
    }

    @Override
    public boolean visit(EqualExpression element) {
        return element.getIdentifier().accept(this) == element.getValue().accept(this);
    }

    @Override
    public boolean visit(NotEqualExpression element) {
        return element.getIdentifier().accept(this) != element.getValue().accept(this);
    }

    @Override
    public boolean visit(GreaterThanExpression element) {
        return element.getIdentifier().accept(this) > element.getValue().accept(this);
    }

    @Override
    public boolean visit(GreaterEqualThanExpression element) {
        return element.getIdentifier().accept(this) >= element.getValue().accept(this);
    }

    @Override
    public boolean visit(SmallerThanExpression element) {
        return element.getIdentifier().accept(this) < element.getValue().accept(this);
    }

    @Override
    public boolean visit(SmallerEqualThanExpression element) {
        return element.getIdentifier().accept(this) <= element.getValue().accept(this);
    }

    @Override
    public int visit(Identifier element) {
        return this.records.get(element.getValue().toCharArray()[0]);
    }

    @Override
    public int visit(Value element) {
        return element.getValue();
    }
}
