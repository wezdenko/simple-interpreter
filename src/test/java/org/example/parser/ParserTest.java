package org.example.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lexer.Lexer;
import org.example.parser.expresion.IExpression;
import org.example.parser.expresion.TopExpression;
import org.example.parser.expresion.booleans.AndExpression;
import org.example.parser.expresion.booleans.OrExpression;
import org.example.parser.expresion.comparators.*;
import org.example.parser.expresion.literals.Identifier;
import org.example.parser.expresion.literals.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        objectMapper = new ObjectMapper();
    }

    void checkParser(String text, IExpression expectedExpressions) throws JsonProcessingException {
        var lexer = new Lexer(text.toCharArray());
        var parser = new Parser(lexer);

        var expressions = parser.parse();

        assertEquals(
                objectMapper.writeValueAsString(expectedExpressions),
                objectMapper.writeValueAsString(expressions)
        );
    }

    static Stream<Arguments> provideForParseComparison() {
        return Stream.of(
                Arguments.of(
                        "a=1",
                        new TopExpression(
                                new EqualExpression(new Identifier("a"), new Value(1))
                        )
                ),
                Arguments.of(
                        "a!=1",
                        new TopExpression(
                               new NotEqualExpression(new Identifier("a"), new Value(1))
                        )
                ),
                Arguments.of(
                        "a<1",
                        new TopExpression(
                                new SmallerThanExpression(new Identifier("a"), new Value(1))
                        )
                ),
                Arguments.of(
                        "a<=1",
                        new TopExpression(
                                new SmallerEqualThanExpression(new Identifier("a"), new Value(1))
                        )
                ),
                Arguments.of(
                        "a>1",
                        new TopExpression(
                                new GreaterThanExpression(new Identifier("a"), new Value(1))
                        )
                ),
                Arguments.of(
                        "a>=1",
                        new TopExpression(
                                new GreaterEqualThanExpression(new Identifier("a"), new Value(1))
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideForParseComparison")
    void parseComparisonTest(String text, IExpression expectedExpressions) throws JsonProcessingException {
        checkParser(text, expectedExpressions);
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        "a=1 or b=0",
                        new TopExpression(
                                new OrExpression(
                                        new EqualExpression(new Identifier("a"), new Value(1)),
                                        new EqualExpression(new Identifier("b"), new Value(0))
                                )
                        )
                ),
                Arguments.of(
                        "a=1 and b=0",
                        new TopExpression(
                                new AndExpression(
                                        new EqualExpression(new Identifier("a"), new Value(1)),
                                        new EqualExpression(new Identifier("b"), new Value(0))
                                )
                        )
                ),
                Arguments.of(
                        "a=1 or b=0 and b=1",
                        new TopExpression(
                                new OrExpression(
                                        new EqualExpression(new Identifier("a"), new Value(1)),
                                        new AndExpression(
                                                new EqualExpression(new Identifier("b"), new Value(0)),
                                                new EqualExpression(new Identifier("b"), new Value(1))
                                        )
                                )
                        )
                ),
                Arguments.of(
                        "a=1 and b=0 or b=1",
                        new TopExpression(
                                new OrExpression(
                                        new AndExpression(
                                                new EqualExpression(new Identifier("a"), new Value(1)),
                                                new EqualExpression(new Identifier("b"), new Value(0))
                                        ),
                                        new EqualExpression(new Identifier("b"), new Value(1))
                                )
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void parseTest(String text, IExpression expectedExpressions) throws JsonProcessingException {
        checkParser(text, expectedExpressions);
    }

    static Stream<Arguments> provideForParseParenthesis() {
        return Stream.of(
                Arguments.of(
                        "(a=0 or b=1) and c=2",
                        new TopExpression(
                                new AndExpression(
                                        new OrExpression(
                                                new EqualExpression(new Identifier("a"), new Value(0)),
                                                new EqualExpression(new Identifier("b"), new Value(1))
                                        ),
                                        new EqualExpression(new Identifier("c"), new Value(2))
                                )
                        )
                ),
                Arguments.of(
                        "a=0 and (b=1 or c=2)",
                        new TopExpression(
                                new AndExpression(
                                        new EqualExpression(new Identifier("a"), new Value(0)),
                                        new OrExpression(
                                                new EqualExpression(new Identifier("b"), new Value(1)),
                                                new EqualExpression(new Identifier("c"), new Value(2))
                                        )
                                )
                        )
                ),
                Arguments.of(
                        "(a=0 or b=1 and c=2)",
                        new TopExpression(
                                new OrExpression(
                                        new EqualExpression(new Identifier("a"), new Value(0)),
                                        new AndExpression(
                                                new EqualExpression(new Identifier("b"), new Value(1)),
                                                new EqualExpression(new Identifier("c"), new Value(2))
                                        )
                                )
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideForParseParenthesis")
    void parseParenthesisTest(String text, IExpression expectedExpressions) throws JsonProcessingException {
        checkParser(text, expectedExpressions);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "(a=0 and b=1 or c=2",
            "a=0 and b=1) or c=2",
            "() a=0 and b=1 or c=2"
    })
    void parseInvalidParenthesisTest(String text) {
        var lexer = new Lexer(text.toCharArray());
        var parser = new Parser(lexer);

        assertThrows(RuntimeException.class, parser::parse);
    }
}