package interpreter.expression;


import interpreter.Context;

/**
 * Basic interface for expressions with numeric result.
 * It declares two methods:
 * <p/>
 * <ul>
 * <li>
 * value(...) should be used to evaluate expression's value against current global context
 * </li>
 * <li>
 * lambdaValue(...) is a performance optimisation when expression is a part of lambda
 * with 'local' context. This method should be used instead of value() to avoid expensive creation
 * of {@link Context} instance. Contract of array: odds indexes are variables names, evens indexes - variables values
 * </li>
 * </ul>
 */
public interface NumberExpression extends Expression {
    double value(Context context);

    double lambdaValue(Object[] context);
}
