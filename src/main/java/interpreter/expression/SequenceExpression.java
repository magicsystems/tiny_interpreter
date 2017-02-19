package interpreter.expression;

/**
 * Representation for sequence as a expression.
 * Contract requires to create sequence with at least one element. Undefined behaviour in other case.
 * clear() method should be called after iteration end.
 * Example of usage:
 *
 * while(sequence.hasNext()) {
 *     double value = sequence.value(context);
 *     ...
 * }
 *
 * sequence.clear()
 *
 */
public interface SequenceExpression extends Expression {
    boolean hasNext();

    void clear();
}
