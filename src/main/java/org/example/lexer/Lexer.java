package org.example.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {
    private static final Map<Character, TokenType> SINGLE_OPERATOR_MAP = Map.of(
            '=', TokenType.EQUAL,
            '<', TokenType.SMALLER_THAN,
            '>', TokenType.GREATER_THAN,
            '(', TokenType.LEFT_PARENTHESES,
            ')', TokenType.RIGHT_PARENTHESES
    );
    private static final Map<String, TokenType> KEYWORD_MAP = Map.of(
            "and", TokenType.AND,
            "or", TokenType.OR
    );
    private final List<Token> tokens;

    public Lexer() {
        this.tokens = new ArrayList<>();
    }

    public List<Token> createTokens(char[] charsArray) {
        for (int i = 0; i < charsArray.length; i++) {
            if (!(charsArray[i] == ' ')) {
                tokens.add(getToken(charsArray[i], i));
            }
        }
        return tokens;
    }

    private Token getToken(char character, int position) {
        if (SINGLE_OPERATOR_MAP.containsKey(character)) {
            return new Token(
                    SINGLE_OPERATOR_MAP.get(character),
                    position,
                    Character.toString(character)
            );
        }
        return new Token(TokenType.UKNOWN, position, Character.toString(character));
    }
}
