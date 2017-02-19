package interpreter.rule;

import interpreter.Context;
import interpreter.statement.Statement;
import interpreter.statement.VariableStatement;
import interpreter.expression.Expression;
import interpreter.expression.Identifier;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import static interpreter.Util.emptyStatement;

/**
 * Rule for variables declaration:
 * <p>
 * var sequence = {1,4}
 */
public class VariableStatementRule implements Rule {
    private static final String START = "var ";

    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith(START);
    }

    @Override
    public Statement parse(Parser parser, String line, Context context) {
        String newLine = line.substring(START.length(), line.length());
        String[] args = newLine.split("=");
        if (args.length == 2) {
            Identifier identifier = parser.parseVariable(context, args[0]);
            Expression expression = parser.expression(context, args[1]);
            return new VariableStatement(expression, identifier.getName());
        } else {
            context.addException(new ParserError("Invalid var statement declaration '" + line + "'"));
            return emptyStatement();
        }
    }
}
