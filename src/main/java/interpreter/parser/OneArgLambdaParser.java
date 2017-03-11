package interpreter.parser;

import interpreter.Context;
import interpreter.error.ParserError;
import interpreter.expression.Identifier;
import interpreter.expression.NumberExpression;

import static interpreter.Util.emptyOneArgLambda;

/**
 *  Parses expressions like 'x -> x * 2' based on {@link Parser}
 */
public class OneArgLambdaParser {

    public OneArgLambda parse(Parser parser, Context context, String line) {
        int firstLambda = line.indexOf("->");
        if (firstLambda != -1) {
            Identifier identifier = parser.parseLambdaIdentifier(context, line.substring(0, firstLambda));
            Context localContext = new Context();
            localContext.putNumericVariable(identifier.getName(), 1.0);
            NumberExpression expression = parser.numberExpression(localContext,
                    line.substring(firstLambda + 2, line.length()));
            if (localContext.hasErrors()) {
                context.addErrors(localContext.getErrors());
            } else {
                return new OneArgLambda(expression, identifier.getName());
            }
        } else {
            context.addError(new ParserError("There should be '->' in '" + line + "'"));
        }
        return emptyOneArgLambda();
    }
}
