package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean validateInvitation(char option){
        return option == 'Y' || option == 'N';
    }

    public static boolean validateOption(char option){

        return option == 'N' || option == 'J' || option == 'E';
    }

    public static boolean validateInstruction(char instruction){

        return instruction == 'A' || instruction == 'M';
    }

    public static boolean validateCoordinates(String coordinates){

        Pattern pattern = Pattern.compile("([A-E][1-5])");
        Matcher matcher= pattern.matcher(coordinates);
        return matcher.find();

    }

    public static boolean validateIP(String IP){

        Pattern pattern = Pattern.compile("^"
                + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}"
                + "|"
                + "localhost"
                + "|"
                + "(([0-9]{1,3}\\.){3})[0-9]{1,3})"
                + ":"
                + "[0-9]{1,5}$");

        Matcher matcher = pattern.matcher(IP);

        return !matcher.matches();
    }
}
