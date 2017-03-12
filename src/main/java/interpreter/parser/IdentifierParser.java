package interpreter.parser;

import interpreter.Context;
import interpreter.error.ParserError;
import interpreter.expression.Identifier;

import static interpreter.Util.isValidIdentifier;
import static interpreter.Util.keyword;

/**
 *  Parses strings to program identifiers - variables or lambda parameters.
 *
 *  Valid identifier consists of only literal chars.
 */
public class IdentifierParser {

    public Identifier parseLambdaParameter(String line, Context context) {
        if (!keyword(line) && isValidIdentifier(line)) {
            return new Identifier(line);
        }
        return errorResponse(context, "Invalid identifier '" + line + "'");
    }

    public Identifier parseVariable(String line, Context context) {
        if (!keyword(line)) {
            if (context.hasNumericVariable(line) || context.hasSequence(line)) {
                return errorResponse(context, "Variable '" + line + "' already defined in the current context");
            } else if (isValidIdentifier(line)) {
                return new Identifier(line);
            }
        }
        return errorResponse(context, "Invalid identifier '" + line + "'");
    }

    private static Identifier errorResponse(Context context, String error) {
        context.addError(new ParserError(error));
        return new Identifier("empty");
    }
}
