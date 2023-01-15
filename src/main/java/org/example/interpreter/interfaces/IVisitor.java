package org.example.interpreter.interfaces;

import org.example.parser.expresion.TopExpression;
import org.example.parser.expresion.booleans.AndExpression;
import org.example.parser.expresion.booleans.OrExpression;
import org.example.parser.expresion.comparators.*;
import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

public interface IVisitor {

    boolean visit(TopExpression element);
    boolean visit(OrExpression element);
    boolean visit(AndExpression element);
    boolean visit(EqualExpression element);
    boolean visit(NotEqualExpression element);
    boolean visit(GreaterThanExpression element);
    boolean visit(GreaterEqualThanExpression element);
    boolean visit(SmallerThanExpression element);
    boolean visit(SmallerEqualThanExpression element);
    int visit(Identifier element);
    int visit(Value element);
}
