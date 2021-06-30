package errors;

import java.util.ArrayList;

public class Error {

    private ArrayList<String> errors = createList();

    private ArrayList<String> createList(){
        return errors = new ArrayList<>();
    }

    public ArrayList<String> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        return errors;
    }

    public int hasError() {
        return getErrors().size();
    }

    public void addError(String description) {
        getErrors().add(description);
    }

    public void addErrors(ArrayList<String> errors) {
        getErrors().addAll(errors);
    }
}
