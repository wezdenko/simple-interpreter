package org.example.parser.exception;

public class ParserException extends RuntimeException {

    public ParserException(String tokenValue, int position) {
        super(String.format("Invalid token '%s' at position %d", tokenValue, position));
    }
}
