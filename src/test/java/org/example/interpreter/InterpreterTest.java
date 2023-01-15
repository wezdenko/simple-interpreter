package org.example.interpreter;

import org.example.lexer.Lexer;
import org.example.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    void run() {
        Map<Character, Integer> records = Map.of(
                'a', 0,
                'b', 0,
                'c', 0,
                'd', 0,
                'e', 0,
                'f', 0,
                'g', 0,
                'h', 0
        );
        var text = "a=0 or b=0 and c=1";

        var interpreter = new Interpreter(records);
        var lexer = new Lexer(text.toCharArray());
        var parser = new Parser(lexer);

        var result = interpreter.run(parser.parse());
        assertTrue(result);
    }
}