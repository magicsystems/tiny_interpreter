package interpreter.rule;

import interpreter.Context;
import interpreter.expression.BracketExpression;
import interpreter.expression.Expression;
import interpreter.expression.NumberExpression;
import interpreter.parser.Parser;

/**
 * Rule for recognition expressions inside brackets:
 * <p>
 * (expr)
 */
public class BracketRule implements Rule {
    @Override
    public boolean couldBeApplied(String line) {
        if (line.startsWith("(") && line.endsWith(")")) {
            int nextOpeningBracket = line.indexOf("(", 1);
            int nextClosingBracket = line.indexOf(")");
            return nextOpeningBracket <= nextClosingBracket;
        }
        return false;
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        String newLine = line.substring(1, line.length() - 1);
        NumberExpression expression = parser.numberExpression(context, newLine);
        return new BracketExpression(expression);
    }
}
