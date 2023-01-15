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
        var expression = parseOrExpression();
        if (expression == null || !isCurrentTokenOfType(TokenType.EOF)) {
            throw new RuntimeException();
        }
        return expression;
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
        var leftExpression = parseParenthesesOrComparisonExpression();

        if (leftExpression == null) return null;

        while (checkAndConsume(TokenType.AND)) {
            var rightExpression = parseParenthesesOrComparisonExpression();
            if (rightExpression == null) {
                throw new RuntimeException();
            }

            leftExpression = new AndExpression(leftExpression, rightExpression);
        }
        return leftExpression;
    }

    private IExpression parseParenthesesOrComparisonExpression() {
        var expression = parseParenthesesExpression();
        if (expression == null) {
            expression = parseComparisonExpression();
        }
        return expression;
    }

    private IExpression parseParenthesesExpression() {
        if (!checkAndConsume(TokenType.LEFT_PARENTHESES)) return null;

        var expression = parseOrExpression();

        if (!checkAndConsume(TokenType.RIGHT_PARENTHESES) || expression == null) {
            throw new RuntimeException();
        }
        return expression;
    }

    private IExpression parseComparisonExpression() {
        Identifier identifier = parseIdentifier();

        if (COMPARISON_EXPRESSION_MAP.containsKey(this.currentToken.tokenType)) {
            var expression = COMPARISON_EXPRESSION_MAP.get(this.currentToken.tokenType);
            nextToken();
            return expression.apply(identifier, parseValue());
        }
        return null;
    }

    private Value parseValue() {
        if (isCurrentTokenOfType(TokenType.VALUE)) {
            var token = new Value(Integer.parseInt(this.currentToken.value));
            nextToken();
            return token;
        }
        throw new RuntimeException();
    }

    private Identifier parseIdentifier() {
        if (isCurrentTokenOfType(TokenType.Identifier)) {
            var token = new Identifier(this.currentToken.value);
            nextToken();
            return token;
        }
        throw new RuntimeException();
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
