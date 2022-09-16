package Controllers;


import javafx.scene.control.TextField;

public class Utilities {
    public static boolean onlyContainsNumbers(String text) {
        return (text.matches("[0-9]+"));
    }

    public static boolean max30Chars(String string) {
        return (string.length() <= 30) ? true : false;
    }

    public static boolean validName(String name) {
        return ((name.length() <= 30) && (name.length() > 0));
    }
    public static boolean validEmail(String email) {
        return (email.contains("@") && email.contains("."));
    }

    public static boolean validNonNegative(int number) {
        return (number >= 0);
    }

    public static boolean validEircode(String eircode) {
        return (eircode.length() == 7);
    }


    public static boolean validPositive(int number) {
        return (number > 0);
    }

    public static boolean validIdentifier(String id) {
        return (id.matches("^[A-Z]{1}[0-9]{1}$"));
    }

    public static boolean validPPSN(String id) {
        return (id.matches("^[0-9]{7}[A-Z]{1}$"));
    }

    public static boolean nonEmptyText(TextField tf) {
        return (tf.getText().trim().equals(""));
    }

    public static boolean validBatch(String string) {
        return (string.matches("[a-zA-Z0-9]{6}"));
    }
}
