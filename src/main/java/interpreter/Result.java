package interpreter;


import interpreter.error.ParserError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Result {
    private final Map<String, List<ParserError>> errors;
    private final List<String> output;

    public Result(Map<String, List<ParserError>> errors, List<String> output) {
        this.errors = errors;
        this.output = output;
    }

    public List<ParserError> getAllErrorsList() {
        ArrayList<ParserError> errorArrayList = new ArrayList<>();
        getErrors().values().forEach(errorArrayList::addAll);
        return errorArrayList;
    }

    public List<String> getOutput() {
        return output;
    }

    public Map<String, List<ParserError>> getErrors() {
        return errors;
    }
}
