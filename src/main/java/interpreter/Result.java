package interpreter;


import interpreter.error.ParserError;

import java.util.List;

public class Result {
    private final List<ParserError> exceptionList;
    private final List<String> output;

    public Result(List<ParserError> exceptionList, List<String> output) {
        this.exceptionList = exceptionList;
        this.output = output;
    }

    public List<ParserError> getExceptionList() {
        return exceptionList;
    }

    public List<String> getOutput() {
        return output;
    }
}
