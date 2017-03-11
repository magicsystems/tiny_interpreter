package interpreter;


import interpreter.error.IncompatibleTypeError;
import interpreter.expression.SequenceExpression;
import interpreter.error.ParserError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private Map<String, Double> variables = new HashMap<>();
    private Map<String, SequenceExpression> sequences = new HashMap<>();
    private List<ParserError> exceptions = new ArrayList<>();
    private List<String> output = new ArrayList<>();

    public Context(Context context) {
        this.variables.putAll(context.getVariables());
        this.sequences.putAll(context.getSequences());
    }

    public Context() {

    }

    public double getValue(String name) {
        return variables.get(name);
    }

    public SequenceExpression getSequence(String name) {
        return sequences.get(name);
    }

    public void putNumericVariable(String name, Double value) {
        variables.put(name, value);
    }

    public void putSequenceVariable(String name, SequenceExpression value) {
        sequences.put(name, value);
    }

    public boolean hasNumericVariable(String variable) {
        return variables.containsKey(variable);
    }

    public boolean hasSequence(String variable) {
        return sequences.containsKey(variable);
    }

    public boolean hasException() {
        return !exceptions.isEmpty();
    }

    public boolean hasIncompatibleParseError() {
        return !exceptions.isEmpty() && exceptions.get(0) instanceof IncompatibleTypeError;
    }

    public void clearExceptions() {
        exceptions.clear();
    }

    public void addException(ParserError pe) {
        exceptions.add(pe);
    }

    public void addExceptions(List<ParserError> errors) {
        exceptions.addAll(errors);
    }

    public List<ParserError> getExceptions() {
        return exceptions;
    }

    public void addOutPutString(String out) {
        output.add(out);
    }

    public List<String> getOutput() {
        return output;
    }

    public Map<String, Double> getVariables() {
        return variables;
    }

    public Map<String, SequenceExpression> getSequences() {
        return sequences;
    }
}
