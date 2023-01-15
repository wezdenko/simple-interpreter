package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.lexer.Lexer;
import org.example.parser.Parser;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        var lexer = new Lexer("a=1".toCharArray());
        var parser = new Parser(lexer);

        var expression = parser.parse();

        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        System.out.println(mapper.writeValueAsString(expression));
    }
}