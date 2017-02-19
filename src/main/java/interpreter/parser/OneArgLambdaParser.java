package interpreter.parser;

import interpreter.Context;
import interpreter.error.ParserError;
import interpreter.expression.Expression;
import interpreter.expression.Identifier;

import static interpreter.Util.emptyOneArgLambda;

/**
 *  Parses expressions like 'x -> x * 2' based on {@link Parser}
 */
public class OneArgLambdaParser {

    public OneArgLambda parse(Parser parser, Context context, String line) {
        String[] args = line.split("->");
        if (args.length == 2) {
            Identifier identifier = parser.parseIdentifier(context, args[0]);
            Context localContext = new Context();
            localContext.putNumericVariable(identifier.getName(), 1.0);
            Expression expression = parser.expression(localContext, args[1]);
            if (localContext.hasException()) {
                context.addException(localContext.getExceptions().get(0));
            } else {
                return new OneArgLambda(expression, identifier.getName());
            }
        } else {
            context.addException(new ParserError("Invalid one arg lambda syntax '" + line + "'"));
        }
        return emptyOneArgLambda();
    }
}
