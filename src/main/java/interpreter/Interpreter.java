package interpreter;


import interpreter.statement.Statement;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interpreter for a program text.
 * <p/>
 * Receives program text, splits it and executes line-by-line with help of {@link Parser} and
 * collects errors and output data in the process.
 */
public class Interpreter {
    private final Parser parser = new Parser();

    public Result execute(String text) {
        String[] lines = text.split("\\n");
        return execute(Arrays.asList(lines));
    }

    private Result execute(List<String> lines) {
        Context context = new Context();
        Map<String, List<ParserError>> errors = new HashMap<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            Statement statement = parser.statement(context, line);
            if (context.hasException()) {
                errors.put(line, new ArrayList<>(context.getExceptions()));
                context.clearExceptions();
            } else {
                statement.execute(context);
            }
        }
        return new Result(errors, context.getOutput());
    }
}
