package org.example.lexer;

public enum TokenType {

    // and, or
    AND,
    OR,

    // = != < <= > >=
    EQUAL,
    NOT_EQUAL,
    SMALLER_THAN,
    SMALLER_EQUAL_THAN,
    GREATER_THAN,
    GREATER_EQUAL_THAN,

    // ( )
    LEFT_PARENTHESES,
    RIGHT_PARENTHESES,

    Identifier,
    UNKNOWN
}
