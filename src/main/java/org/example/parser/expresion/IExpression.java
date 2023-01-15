package org.example.parser.expresion;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public interface IExpression {
}
