package org.example;

import org.example.lexer.Lexer;
import org.example.parser.Parser;

public class Main {
    public static void main(String[] args) {
        var lexer = new Lexer("a=1 or b=1 and b=1".toCharArray());
        var parser = new Parser(lexer);

        var expression = parser.parse();

        System.out.println("Finish");
    }
}