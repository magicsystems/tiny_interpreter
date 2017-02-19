package interpreter.parser;

import interpreter.Context;
import interpreter.error.ParserError;
import interpreter.expression.Identifier;

import static interpreter.Util.keyword;


public class IdentifierParser {

    public Identifier parseLambdaParameter(String line, Context context) {
        if (!keyword(line)) {
            return new Identifier(line);
        }
        context.addException(new ParserError("Invalid identifier '" + line + "'"));
        return new Identifier("empty");
    }
}
