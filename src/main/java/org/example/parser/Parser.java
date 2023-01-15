package org.example.parser;

import org.example.lexer.Lexer;
import org.example.lexer.Token;
import org.example.lexer.TokenType;
import org.example.parser.expresion.*;
import org.example.parser.expresion.booleans.AndExpression;
import org.example.parser.expresion.booleans.OrExpression;
import org.example.parser.expresion.comparators.*;
import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;

import java.util.Map;
import java.util.function.BiFunction;

public class Parser {

    private final Lexer lexer;
    private Token currentToken;

    private static final Map<TokenType, BiFunction<Identifier, Value, IExpression>>
        COMPARISON_EXPRESSION_MAP = Map.of(
            TokenType.EQUAL, EqualExpression::new,
            TokenType.NOT_EQUAL, NotEqualExpression::new,
            TokenType.GREATER_THAN, GreaterThanExpression::new,
            TokenType.GREATER_EQUAL_THAN, GreaterEqualThanExpression::new,
            TokenType.SMALLER_THAN, SmallerThanExpression::new,
            TokenType.SMALLER_EQUAL_THAN, SmallerEqualThanExpression::new
    );

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    public IExpression parse() {
        return parseOrExpression();
    }


    private IExpression parseOrExpression() {
        var leftExpression = parseAndExpression();

        if (leftExpression == null) return null;

        while (checkAndConsume(TokenType.OR)) {
            var rightExpression = parseAndExpression();
            if (rightExpression == null) {
                throw new RuntimeException();
            }

            leftExpression = new OrExpression(leftExpression, rightExpression);
        }
        return leftExpression;
    }

    private IExpression parseAndExpression() {
        var leftExpression = parseComparisonExpression();

        if (leftExpression == null) return null;

        while (checkAndConsume(TokenType.AND)) {
            var rightExpression = parseComparisonExpression();
            if (rightExpression == null) {
                throw new RuntimeException();
            }

            leftExpression = new AndExpression(leftExpression, rightExpression);
        }
        return leftExpression;
    }

    private IExpression parseComparisonExpression() {
        Identifier identifier = parseIdentifier();

        if (identifier == null) return null;

        if (COMPARISON_EXPRESSION_MAP.containsKey(this.currentToken.tokenType)) {
            var expression = COMPARISON_EXPRESSION_MAP.get(this.currentToken.tokenType);
            nextToken();

            Value value = parseValue();
            if (value == null) {
                throw new RuntimeException();
            }

            return expression.apply(identifier, value);
        }
        return null;
    }

    private Value parseValue() {
        if (isCurrentTokenOfType(TokenType.VALUE)) {
            var token = new Value(Integer.parseInt(this.currentToken.value));
            nextToken();
            return token;
        }
        return null;
    }

    private Identifier parseIdentifier() {
        if (isCurrentTokenOfType(TokenType.Identifier)) {
            var token = new Identifier(this.currentToken.value);
            nextToken();
            return token;
        }
        return null;
    }

    private boolean checkAndConsume(TokenType tokenType) {
        if (!isCurrentTokenOfType(tokenType)) {
            return false;
        }
        nextToken();
        return true;
    }

    private void nextToken() {
        this.currentToken = this.lexer.getNextToken();
    }

    private boolean isCurrentTokenOfType(TokenType tokenType) {
        return this.currentToken.tokenType.equals(tokenType);
    }
}
