package interpreter.rule;


import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.parser.Parser;

public interface Rule<T> {
    boolean couldBeApplied(String line);

    T parse(Parser parser, String line, Context context);
}
