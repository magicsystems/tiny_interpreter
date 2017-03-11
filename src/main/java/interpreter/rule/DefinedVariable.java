package interpreter.rule;

import interpreter.Context;
import interpreter.error.UndefinedVariableError;
import interpreter.expression.Expression;
import interpreter.expression.Identifier;
import interpreter.parser.Parser;

import static interpreter.Util.emptyExpression;
import static interpreter.Util.isValidIdentifier;
import static interpreter.Util.keyword;

/**
 * Resolves defined {@link Expression} variables from context
 */
public class DefinedVariable implements Rule {

    @Override
    public boolean couldBeApplied(String line) {
        return isValidIdentifier(line);
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        if (!keyword(line)) {
            if (context.hasNumericVariable(line)) {
                return new Identifier(line);
            } else if (context.hasSequence(line)) {
                return context.getSequence(line);
            }
        }
        context.addError(new UndefinedVariableError("Undefined variable '" + line + "'"));
        return emptyExpression();
    }

}
