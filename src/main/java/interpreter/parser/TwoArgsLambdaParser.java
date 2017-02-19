package interpreter.parser;

import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.Identifier;

import static interpreter.Util.emptyTwoArgsLambda;

/**
 *  Parses expressions like 'x y -> y + x * 2 - 3' based on {@link Parser}
 */
public class TwoArgsLambdaParser {

    public TwoArgsLambda parse(Parser parser, Context context, String line) {
        String[] args = line.split("->");
        if (args.length == 2) {
            String identifierStr = args[0].trim();
            String identifiers[] = identifierStr.split(" ");
            if (identifiers.length == 2) {
                Identifier identifier1 = parser.parseIdentifier(context, identifiers[0]);
                Identifier identifier2 = parser.parseIdentifier(context, identifiers[1]);
                Context localContext = new Context();
                localContext.putNumericVariable(identifier1.getName(), 1.0);
                localContext.putNumericVariable(identifier2.getName(), 1.0);
                Expression expression = parser.expression(localContext, args[1]);
                if (localContext.hasException()) {
                    context.addException(localContext.getExceptions().get(0));
                }
                return new TwoArgsLambda(expression,
                        identifier1.getName(), identifier2.getName());
            } else {
                context.addException(new ParserError("Invalid declaration for two identifiers '" + identifierStr + "'"));
            }
        } else {
            context.addException(new ParserError("Invalid two args lambda syntax '" + line + "'"));
        }
        return emptyTwoArgsLambda();
    }
}
