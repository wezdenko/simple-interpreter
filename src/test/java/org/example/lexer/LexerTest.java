package org.example.lexer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class LexerTest {

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        "( ) < > =",
                        List.of(
                                new Token(TokenType.LEFT_PARENTHESES, 0, "("),
                                new Token(TokenType.RIGHT_PARENTHESES, 2, ")"),
                                new Token(TokenType.SMALLER_THAN, 4, "<"),
                                new Token(TokenType.GREATER_THAN, 6, ">"),
                                new Token(TokenType.EQUAL, 8, "="),
                                new Token(TokenType.EOF, 9, "\0")
                        )
                ),
                Arguments.of(
                        "<= >= !=",
                        List.of(
                                new Token(TokenType.SMALLER_EQUAL_THAN, 0, "<="),
                                new Token(TokenType.GREATER_EQUAL_THAN, 3, ">="),
                                new Token(TokenType.NOT_EQUAL, 6, "!="),
                                new Token(TokenType.EOF, 8, "\0")
                        )
                ),
                Arguments.of(
                        "a b c d e f g h",
                        List.of(
                                new Token(TokenType.Identifier, 0, "a"),
                                new Token(TokenType.Identifier, 2, "b"),
                                new Token(TokenType.Identifier, 4, "c"),
                                new Token(TokenType.Identifier, 6, "d"),
                                new Token(TokenType.Identifier, 8, "e"),
                                new Token(TokenType.Identifier, 10, "f"),
                                new Token(TokenType.Identifier, 12, "g"),
                                new Token(TokenType.Identifier, 14, "h"),
                                new Token(TokenType.EOF, 15, "\0")
                        )
                ),
                Arguments.of(
                        "i ab a=b",
                        List.of(
                                new Token(TokenType.UNKNOWN, 0, "i"),
                                new Token(TokenType.UNKNOWN, 2, "ab"),
                                new Token(TokenType.Identifier, 5, "a"),
                                new Token(TokenType.EQUAL, 6, "="),
                                new Token(TokenType.Identifier, 7, "b"),
                                new Token(TokenType.EOF, 8, "\0")
                        )
                ),
                Arguments.of(
                        "  <   >  ",
                        List.of(
                                new Token(TokenType.SMALLER_THAN, 2, "<"),
                                new Token(TokenType.GREATER_THAN, 6, ">"),
                                new Token(TokenType.EOF, 9, "\0")
                        )
                ),
                Arguments.of(
                        "a=123 or",
                        List.of(
                                new Token(TokenType.Identifier, 0, "a"),
                                new Token(TokenType.EQUAL, 1, "="),
                                new Token(TokenType.VALUE, 2, "123"),
                                new Token(TokenType.OR, 6, "or"),
                                new Token(TokenType.EOF, 8, "\0")
                        )
                ),
                Arguments.of(
                        "",
                        List.of()
                ),
                Arguments.of(
                        "\t   ",
                        List.of(
                                new Token(TokenType.EOF, 4, "\0")
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void getNextToken(String text, List<Token> expectedTokens) {
        var lexer = new Lexer(text.toCharArray());
        expectedTokens.forEach(expectedToken -> {
            var token = lexer.getNextToken();
            Assertions.assertEquals(token.tokenType, expectedToken.tokenType);
            Assertions.assertEquals(token.position, expectedToken.position);
            Assertions.assertEquals(token.value, expectedToken.value);
        });
    }
}