package compiler;

import java.util.*;

public class ErrorHandler {
    private List<String> errors = new ArrayList<>();

    public void reportError(int line, String message) {
        errors.add(String.format("Line %d: %s", line, message));
    }

    public List<String> getErrors() {
        return errors;
    }

    public void printErrors() {
        for (String error : errors) {
            System.out.println(error);
        }
    }
}