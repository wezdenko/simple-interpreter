package org.example.lexer;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Lexer {
    private static final Map<Character, TokenType> SINGLE_OPERATOR_MAP = Map.of(
            '=', TokenType.EQUAL,
            '(', TokenType.LEFT_PARENTHESES,
            ')', TokenType.RIGHT_PARENTHESES
    );
    private static final Map<String, TokenType> KEYWORD_MAP = Map.of(
            "and", TokenType.AND,
            "or", TokenType.OR
    );

    private static final Map<Character, Function<Character, TokenType>> MULTI_OPERATOR_MAP = Map.of(
            '<', nextCharacter -> (nextCharacter == '=' ? TokenType.SMALLER_EQUAL_THAN : TokenType.SMALLER_THAN),
            '>', nextCharacter -> (nextCharacter == '=' ? TokenType.GREATER_EQUAL_THAN : TokenType.GREATER_THAN),
            '!', nextCharacter -> (nextCharacter == '=' ? TokenType.NOT_EQUAL : TokenType.UNKNOWN)
    );

    private static final Map<TokenType, String> REVERSED_MULTI_OPERATOR_MAP = Map.of(
            TokenType.EQUAL, "=",
            TokenType.NOT_EQUAL, "!=",
            TokenType.SMALLER_THAN, "<",
            TokenType.GREATER_THAN, ">",
            TokenType.SMALLER_EQUAL_THAN, "<=",
            TokenType.GREATER_EQUAL_THAN, ">="
    );

    private static final Set<String> IDENTIFIERS = Set.of("a", "b", "c", "d", "e", "f", "g", "h");

    private static final Set<TokenType> MULTI_OPERATORS_TOKEN_TYPES = Set.of(
            TokenType.GREATER_EQUAL_THAN,
            TokenType.SMALLER_EQUAL_THAN,
            TokenType.NOT_EQUAL
    );

    private final char[] buffer;
    private int position;
    private char character;

    public Lexer(char[] charsArray) {
        this.buffer = charsArray;
        this.position = 0;

        if (this.buffer.length > 0) {
            this.character = this.buffer[position];
        } else {
            this.character = '\0';
        }
    }

    public Token getNextToken() {
        skipWhiteSpaces();
        var token = getToken();
        nextCharacter();
        return token;
    }

    private void nextCharacter() {
        this.position++;

        if (this.position >= this.buffer.length) {
            this.character = '\0';
            return;
        }
        this.character = this.buffer[position];
    }

    private void previousCharacter() {
        this.position--;

        if (this.position < 0) {
            this.character = '\0';
            return;
        }
        this.character = this.buffer[position];
    }

    private char getNextCharacter() {
        if (this.position + 1 >= this.buffer.length) {
            return '\0';
        }
        return this.buffer[position + 1];
    }

    private void skipWhiteSpaces() {
        while (Character.isWhitespace(this.character)) {
            nextCharacter();
        }
    }

    private Token getToken() {
        if (SINGLE_OPERATOR_MAP.containsKey(this.character)) {
            return new Token(
                    SINGLE_OPERATOR_MAP.get(this.character),
                    position,
                    Character.toString(this.character)
            );
        }
        if (MULTI_OPERATOR_MAP.containsKey(this.character)) {
            var currentPosition = this.position;
            var determineOperator = MULTI_OPERATOR_MAP.get(this.character);
            var tokenType = determineOperator.apply(getNextCharacter());
            if (MULTI_OPERATORS_TOKEN_TYPES.contains(tokenType)) {
                nextCharacter();
            }
            return new Token(tokenType, currentPosition, REVERSED_MULTI_OPERATOR_MAP.get(tokenType));
        }
        if (this.character == '\0') {
            return new Token(TokenType.EOF, this.position, "\0");
        }
        if (Character.isDigit(this.character)) {
            return getValue();
        }
        return getKeywordOrIdentifier();
    }

    private Token getValue() {
        var stringBuilder = new StringBuilder();
        var startPosition = this.position;

        while(Character.isDigit(this.character)) {
            stringBuilder.append(this.character);
            nextCharacter();
        }
        previousCharacter();

        var stringValue = stringBuilder.toString();
        return new Token(TokenType.VALUE, startPosition, stringValue);
    }

    private Token getKeywordOrIdentifier() {
        var stringBuilder = new StringBuilder();
        var startPosition = this.position;

        while(Character.isLetter(this.character)) {
            stringBuilder.append(this.character);
            nextCharacter();
        }
        previousCharacter();

        var stringValue = stringBuilder.toString();

        if (KEYWORD_MAP.containsKey(stringValue)) {
            return new Token(KEYWORD_MAP.get(stringValue), startPosition, stringValue);
        }
        if (IDENTIFIERS.contains(stringValue)) {
            return new Token(TokenType.Identifier, startPosition, stringValue);
        }
        return new Token(TokenType.UNKNOWN, startPosition, stringValue);
    }
}
