package interpreter.rule;

import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.parser.Parser;
import interpreter.expression.Number;
import interpreter.error.ParserError;

import static interpreter.Util.emptyNumberExpression;

/**
 * Simplest possible resolving for double values
 */
public class NumberRule implements Rule {
    @Override
    public boolean couldBeApplied(String line) {
        return parse(line) != null;
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        Double result = parse(line);
        if (result != null) {
            return new Number(result);

        } else {
            context.addError(new ParserError("Invalid number '" + line + "'"));
            return emptyNumberExpression();
        }
    }

    private static Double parse(String line) {
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
