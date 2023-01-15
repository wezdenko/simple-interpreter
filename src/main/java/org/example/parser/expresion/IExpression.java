package org.example.parser.expresion;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.interpreter.interfaces.IVisitable;

@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use= JsonTypeInfo.Id.NAME)
public interface IExpression extends IVisitable {
}
