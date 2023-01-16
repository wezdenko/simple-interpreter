package org.example;

import org.example.interpreter.Interpreter;
import org.example.lexer.Lexer;
import org.example.parser.Parser;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final Map<Character, Integer> records = Map.of(
                'a', 1,
                'b', 1,
                'c', 1,
                'd', 1,
                'e', 0,
                'f', 0,
                'g', 0,
                'h', 0
        );

        var text = "a=1 or b>1";

        var lexer = new Lexer(text.toCharArray());
        var parser = new Parser(lexer);
        var interpreter = new Interpreter(records);

        var result = interpreter.run(parser.parse());

        System.out.println(result);
    }
}