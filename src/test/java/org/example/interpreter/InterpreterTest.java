package org.example.interpreter;

import org.example.lexer.Lexer;
import org.example.parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    private final static Map<Character, Integer> RECORDS = Map.of(
            'a', 0,
            'b', 1,
            'c', 2,
            'd', 3,
            'e', 0,
            'f', 0,
            'g', 0,
            'h', 0
    );

    private boolean getResult(String text) {
        var interpreter = new Interpreter(RECORDS);
        var lexer = new Lexer(text.toCharArray());
        var parser = new Parser(lexer);

        return interpreter.run(parser.parse());
    }

    static Stream<Arguments> provideArguments1() {
        return Stream.of(
                Arguments.of("a=0", true),
                Arguments.of("a=1", false),
                Arguments.of("a!=1", true),
                Arguments.of("a!=0", false),
                Arguments.of("b>0", true),
                Arguments.of("b>1", false),
                Arguments.of("b>=1", true),
                Arguments.of("b>=2", false),
                Arguments.of("b<2", true),
                Arguments.of("b<1", false),
                Arguments.of("b<=1", true),
                Arguments.of("b<=0", false)
        );
    }

    @ParameterizedTest
    @DisplayName("Test comparison expressions")
    @MethodSource("provideArguments1")
    void runTest1(String text, boolean expectedResult) {
        assertEquals(expectedResult, getResult(text));
    }

    static Stream<Arguments> provideArguments2() {
        return Stream.of(
                Arguments.of("a=0 or b=0", true),
                Arguments.of("a=1 or b=0", false),
                Arguments.of("a=0 and b=1", true),
                Arguments.of("a=0 and b=0", false)
        );
    }

    @ParameterizedTest
    @DisplayName("Test boolean expressions")
    @MethodSource("provideArguments2")
    void runTest2(String text, boolean expectedResult) {
        assertEquals(expectedResult, getResult(text));
    }
}