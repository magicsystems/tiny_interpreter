package interpreter.parser;

import interpreter.Context;
import interpreter.error.ParserError;
import interpreter.expression.Identifier;
import interpreter.expression.NumberExpression;

import static interpreter.Util.emptyTwoArgsLambda;

/**
 *  Parses expressions like 'x y -> y + x * 2 - 3' based on {@link Parser}
 */
public class TwoArgsLambdaParser {

    public TwoArgsLambda parse(Parser parser, Context context, String line) {
        int firstLambda = line.indexOf("->");
        if (firstLambda != -1) {
            String identifierStr = line.substring(0, firstLambda);
            String identifiers[] = identifierStr.split(" +");
            if (identifiers.length == 2) {
                Identifier identifier1 = parser.parseLambdaIdentifier(context, identifiers[0]);
                Identifier identifier2 = parser.parseLambdaIdentifier(context, identifiers[1]);
                Context localContext = new Context();
                localContext.putNumericVariable(identifier1.getName(), 1.0);
                localContext.putNumericVariable(identifier2.getName(), 1.0);
                NumberExpression expression = parser.numberExpression(localContext, line.substring(firstLambda + 2,
                        line.length()));
                if (localContext.hasException()) {
                    context.addExceptions(localContext.getExceptions());
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
