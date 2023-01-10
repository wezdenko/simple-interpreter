package org.example.lexer;

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
}
