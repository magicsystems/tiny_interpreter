package interpreter.expression;

import interpreter.Context;

import java.util.stream.DoubleStream;

/**
 * Expression representation for a reduce() function.
 * <p/>
 * On execution converts underline sequence into parallel {@link DoubleStream}
 */
public class ReduceExpression implements NumberExpression {
    private SequenceExpression sequence;
    private NumberExpression identity;
    private NumberExpression expression;
    private String firstIdentifier;
    private String secondIdentifier;

    public ReduceExpression(SequenceExpression sequence, NumberExpression identity,
                            String firstIdentifier, String secondIdentifier,
                            NumberExpression expression) {
        this.sequence = sequence;
        this.identity = identity;
        this.expression = expression;
        this.firstIdentifier = firstIdentifier;
        this.secondIdentifier = secondIdentifier;
    }

    @Override
    public double value(Context context) {
        DoubleStream stream = sequence.stream(context);
        double identityValue = identity.value(context);
        return stream.parallel().reduce(identityValue, (x, y) -> {
           Object[] array = new Object[4];
            array[0] = firstIdentifier;
            array[1] = x;
            array[2] = secondIdentifier;
            array[3] = y;
            return expression.lambdaValue(array);
        });
    }

    @Override
    public double lambdaValue(Object[] array) {
        Context context = new Context();
        for (int i = 0; i < array.length; i+=2) {
            context.putNumericVariable((String)array[i], (Double)array[i + 1]);

        }
        return value(context);
    }

    public String toString() {
        return "reduce(" + sequence + ", " + identity + ", (" +
                firstIdentifier + ", " + secondIdentifier + ") ->" + expression + ")";
    }
}
