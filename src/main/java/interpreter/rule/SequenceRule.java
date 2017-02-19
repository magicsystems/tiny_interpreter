package interpreter.rule;


import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.Sequence;
import interpreter.parser.Parser;
import interpreter.parser.ParserError;

import static interpreter.Util.emptyExpression;

/**
 * Rule for sequence expressions:
 * <p>
 *  {1, 6} or {1, n}
 */

public class SequenceRule implements Rule {
    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith("{") && line.endsWith("}");
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        String newLine = line.substring(1, line.length() - 1);
        String[] args = newLine.split(",");
        if (args.length == 2) {
            Expression left = parser.expression(context, args[0]);
            Expression right = parser.expression(context, args[1]);
            return new Sequence(left, right, x -> x + 1);
        } else {
            context.addException(new ParserError("Invalid sequence '" + newLine + ""));
            return emptyExpression();
        }
    }
}
