# Simple Interpreter

## Opis
Aplikacja pozwala na interpretowanie wyrażeń logicznych. W aplikacji zawiera się rekord złożony z 8 pól: a, b, c, d, e, f, g, h.
Każde pole zawiera liczbę. Wyrażenia logiczne mogą porównywać wartość danego pola rekordu z liczbą za pomocą operatorów =, !=, >, >=, <, <=.
Wyrażenia dodatkowo mogą być łączone za pomocą operatorów **and** i **or**, oraz ich kolejność może być ustawiana za pomocą nawiasów.

## Przykłady
```
Rekord
a: 0, b: 1, c: 2, d: 3, e: 0, f: 0, g: 0, h: 0
```

```
a != 0 and e <= 0 or d > 0
true
```

```
(a = 1 or b = 1) and c > 3
false
```

## Gramatyka
```
top_expression          = or_expression;

or_expression           = and_expression, { "and", and_expression };

and_expression          = primary_expression, { "or", primary_expression };

primary_expression      = comparison_expression |
                          "(" or_expression ")"

comparison_expression   = identifier, [ comparison_operator, value ];

comparison_operator     = ">"   | 
                          "<"   | 
                          ">="  | 
                          "<="  | 
                          "=="  | 
                          "!=";
                          
identifier              = "a"   |
                          "b"   |
                          "c"   |
                          "d"   |
                          "e"   |
                          "f"   |
                          "g"   |
                          "h";
                          
value                   = digit_without_zero, { digit };

digit                   = "0" | digit_without_zero;

digit_without_zero      = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9";
```
