package interpreter.expression;

import interpreter.Context;
import interpreter.Util;

import java.util.stream.DoubleStream;

/**
 * Expression representation for a reduce() function.
 *
 * On execution converts underline sequence into parallel {@link DoubleStream}
 */
public class ReduceExpression implements NumberExpression {
    private SequenceExpression sequence;
    private Expression identity;
    private Expression expression;
    private String firstIdentifier;
    private String secondIdentifier;

    public ReduceExpression(SequenceExpression sequence, Expression identity,
                            String firstIdentifier, String secondIdentifier,
                            Expression expression) {
        this.sequence = sequence;
        this.identity = identity;
        this.expression = expression;
        this.firstIdentifier = firstIdentifier;
        this.secondIdentifier = secondIdentifier;
    }

    @Override
    public double value(Context context) {
        DoubleStream stream = Util.sequenceToStream(sequence, context);
        double answer = stream.parallel().reduce(identity.value(context), (x, y) -> {
            Context internalContext = new Context();
            internalContext.putNumericVariable(firstIdentifier, x);
            internalContext.putNumericVariable(secondIdentifier, y);
            return expression.value(internalContext);
        });
        sequence.clear();
        return answer;
    }

    public String toString() {
        return "reduce(" + sequence + ", " + identity + ", (" +
                firstIdentifier + ", " + secondIdentifier + ") ->" + expression + ")";
    }

}
