package org.example.lexer;

import java.util.Objects;

public class Token {
    public TokenType tokenType;
    public int position;
    public String value;

    public Token() {
    }

    public Token(TokenType tokenType, int position, String value) {
        this.tokenType = tokenType;
        this.position = position;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return position == token.position
                && tokenType == token.tokenType
                && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, position, value);
    }
}
