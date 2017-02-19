package interpreter.expression;


import interpreter.Context;
import interpreter.Util;

import java.util.Iterator;
import java.util.stream.DoubleStream;

/**
 * Expression representation for a map() function.
 * <p>
 * On execution converts underline sequence into parallel {@link DoubleStream}
 */
public class MapExpression implements SequenceExpression {
    private final SequenceExpression sequence;
    private final Expression arithmeticExpression;
    private final String identifier;

    private Iterator<Double> result;

    public MapExpression(SequenceExpression sequence, String identifier, Expression arithmeticExpression) {
        this.sequence = sequence;
        this.arithmeticExpression = arithmeticExpression;
        this.identifier = identifier;
    }

    @Override
    public double value(Context context) {
        if (result == null) {
            result = calculate(context);
        }
        return result.next();
    }

    @Override
    public boolean hasNext() {
        return result == null || result.hasNext();
    }

    @Override
    public void clear() {
        sequence.clear();
        result = null;
    }

    public String toString() {
        return "map(" + sequence + ", " + identifier + " -> " + arithmeticExpression + ")";
    }

    private Iterator<Double> calculate(Context context) {
        DoubleStream stream = Util.sequenceToStream(sequence, context);
        return stream.parallel().map(x -> {
            Context internalContext = new Context();
            internalContext.putNumericVariable(identifier, x);
            return arithmeticExpression.value(internalContext);
        }).iterator();
    }
}
