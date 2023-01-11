package org.example.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            '<', nextCharacter -> (nextCharacter == '=' ? TokenType.GREATER_EQUAL_THAN : TokenType.GREATER_THAN),
            '>', nextCharacter -> (nextCharacter == '=' ? TokenType.SMALLER_EQUAL_THAN : TokenType.SMALLER_THAN),
            '!', nextCharacter -> (nextCharacter == '=' ? TokenType.NOT_EQUAL : TokenType.UKNOWN)
    );

    private static final Map<TokenType, String> REVERSED_MULTI_OPERATOR_MAP = Map.of(
            TokenType.EQUAL, "=",
            TokenType.NOT_EQUAL, "!=",
            TokenType.SMALLER_THAN, "<",
            TokenType.GREATER_THAN, ">",
            TokenType.SMALLER_EQUAL_THAN, "<=",
            TokenType.GREATER_EQUAL_THAN, ">="
    );

    private final List<Token> tokens;
    private char[] buffer;
    private int position;
    private char character;

    public Lexer() {
        this.tokens = new ArrayList<>();
        this.position = 0;
        this.character = '\0';
    }

    public List<Token> createTokens(char[] charsArray) {
        this.buffer = charsArray;
        this.character = this.buffer[0];

        while (this.position < this.buffer.length) {
            skipWhiteSpaces();
            addToken();
            nextCharacter();
        }
        return tokens;
    }

    private void nextCharacter() {
        this.position++;

        if (this.position >= this.buffer.length) {
            this.character = '\0';
            return;
        }
        this.character = this.buffer[position];
    }

    private void skipWhiteSpaces() {
        while (this.character == ' ') {
            nextCharacter();
        }
    }

    private void addToken() {
        if (this.character != '\0') {
            this.tokens.add(getToken());
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
            var determineOperator = MULTI_OPERATOR_MAP.get(this.character);
            this.nextCharacter();
            var tokenType = determineOperator.apply(this.character);
            return new Token(tokenType, position, REVERSED_MULTI_OPERATOR_MAP.get(tokenType));
        }
        return new Token(TokenType.UKNOWN, position, Character.toString(this.character));
    }
}
