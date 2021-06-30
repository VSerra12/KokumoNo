package manager;

import errors.Error;

import java.util.ArrayList;

public abstract class BaseManager {
    
    private static Error errors;

    public BaseManager() {
        errors = new Error();
    }

    public static ArrayList<String> getErrors() {
        return errors.getErrors();
    }

    public static int hasError() {
        return errors.hasError();
    }

    public static void addError(String description) {
        errors.addError(description);
    }

    public void addErrors(ArrayList<String> errors) {
        errors.addAll(errors);
    }

}
