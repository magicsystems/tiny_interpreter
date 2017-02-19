package interpreter;


import interpreter.statement.Statement;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import java.util.ArrayList;
import java.util.List;

/**
 * Interpreter for a program text.
 * <p>
 * Receives program text, splits it and executes line-by-line with help of {@link Parser} and
 * collects errors and output data in the process.
 */
public class Interpreter {
    private final Parser parser = new Parser();

    public Result execute(String text) {
        String[] lines = text.split("\\n");
        Context context = new Context();
        List<ParserError> exceptions = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            Statement statement = parser.statement(context, line);
            if (context.hasException()) {
                exceptions.addAll(context.getExceptions());
                context.clearExceptions();
            } else {
                statement.execute(context);
            }
        }
        return new Result(exceptions, context.getOutput());
    }
}
