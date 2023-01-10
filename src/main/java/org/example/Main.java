package org.example;

import org.example.lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        var lexer = new Lexer();
        var tokens = lexer.createTokens("(< > = = >))".toCharArray());
        System.out.println("Hello");
    }
}