package interpreter.expression;


import interpreter.Context;
import java.util.stream.DoubleStream;

/**
 * Expression representation for a map() function.
 * <p>
 * On execution converts underline sequence into parallel {@link DoubleStream}
 */
public class MapExpression implements SequenceExpression {
    private final SequenceExpression sequence;
    private final NumberExpression arithmeticExpression;
    private final String identifier;

    public MapExpression(SequenceExpression sequence, String identifier, NumberExpression arithmeticExpression) {
        this.sequence = sequence;
        this.arithmeticExpression = arithmeticExpression;
        this.identifier = identifier;
    }

    @Override
    public DoubleStream stream(Context context) {
        DoubleStream stream = this.sequence.stream(context);
        return stream.parallel().map(x -> {
            Object[] array = new Object[2];
            array[0] = identifier;
            array[1] = x;
            return arithmeticExpression.lambdaValue(array);
        });
    }

    public String toString() {
        return "map(" + sequence + ", " + identifier + " -> " + arithmeticExpression + ")";
    }
}
