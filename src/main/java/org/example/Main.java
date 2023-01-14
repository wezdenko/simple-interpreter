package org.example;

import org.example.lexer.Lexer;

public class Main {
    public static void main(String[] args) {
        var lexer = new Lexer("and a or dkwadokwaod".toCharArray());

        System.out.println(lexer.getNextToken().tokenType);
        System.out.println(lexer.getNextToken().tokenType);
        System.out.println(lexer.getNextToken().tokenType);
        System.out.println(lexer.getNextToken().tokenType);
    }
}