package interpreter.rule;

import interpreter.Context;
import interpreter.expression.NumberExpression;
import interpreter.statement.NoOpExpressionStatement;
import interpreter.statement.Statement;
import interpreter.statement.OutNumberExpressionStatement;
import interpreter.statement.OutSequenceStatement;
import interpreter.expression.Expression;
import interpreter.expression.SequenceExpression;
import interpreter.parser.Parser;

/**
 * Prints expressions - number and sequence:
 * <p/>
 * out {1,8}
 */
public class OutRule implements Rule {
    private static final String START = "out ";

    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith("out ");
    }

    @Override
    public Statement parse(Parser parser, String line, Context context) {
        String newLine = line.substring(START.length(), line.length());
        Expression expression = parser.expression(context, newLine);
        if (expression instanceof SequenceExpression) {
            return new OutSequenceStatement((SequenceExpression) expression);
        } else if (expression instanceof NumberExpression) {
            return new OutNumberExpressionStatement((NumberExpression) expression);
        } else {
            return new NoOpExpressionStatement();
        }
    }
}
