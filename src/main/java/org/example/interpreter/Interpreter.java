package org.example.interpreter;

import org.example.interpreter.interfaces.IVisitor;
import org.example.parser.expresion.TopExpression;

import java.util.Map;

public class Interpreter {

    private final IVisitor interpreterVisitor;

    public Interpreter(Map<Character, Integer> records) {
        this.interpreterVisitor = new InterpreterVisitor(records);
    }

    public boolean run(TopExpression expression) {
        return this.interpreterVisitor.visit(expression);
    }
}
