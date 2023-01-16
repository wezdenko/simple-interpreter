package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.interpreter.Interpreter;
import org.example.lexer.Lexer;
import org.example.parser.Parser;
import org.example.parser.exception.ParserException;
import org.example.parser.expresion.IExpression;
import org.example.parser.expresion.TopExpression;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Map<Character, Integer> records = Map.of(
                'a', 1,
                'b', 1,
                'c', 1,
                'd', 1,
                'e', 0,
                'f', 0,
                'g', 0,
                'h', 0
        );

        System.out.println("Records:");
        System.out.println(records);
        System.out.println("Write an expression:");

        var input = new Scanner(System.in);
        var text = input.nextLine();

        var lexer = new Lexer(text.toCharArray());
        var parser = new Parser(lexer);
        var interpreter = new Interpreter(records);

        TopExpression expression;
        try {
            expression = parser.parse();
        } catch (ParserException e) {
            System.out.println("ERROR! Invalid expression:");
            System.out.println(e.getMessage());
            return;
        }

        printTree(input, expression);

        System.out.println("Result of the expression: ");
        System.out.println(interpreter.run(expression));
    }

    private static void printTree(Scanner input, IExpression expression) {
        System.out.println("Print tree in console? (Y/N)");
        while (true) {
            var answer = input.nextLine();
            if (isYesNo(answer)) {
                if (mapYesNoToBoolean(answer)) {
                    System.out.println(getExpressionAsJson(expression));
                }
                break;
            }
            System.out.println("Wrong value. Use (Y/N)");
        }
    }

    private static boolean isYesNo(String string) {
        return Set.of("yes", "no", "y", "n").contains(string.toLowerCase());
    }

    private static boolean mapYesNoToBoolean(String string) {
        Map<String, Boolean> yesNoMap = Map.of(
                "yes", true,
                "y", true,
                "no", false,
                "n", false
        );

        return yesNoMap.get(string.toLowerCase());
    }

    private static String getExpressionAsJson(IExpression expression) {
        var mapper = new JsonMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(expression);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}