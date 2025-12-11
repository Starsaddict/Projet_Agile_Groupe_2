package util;

public class emailUtil {

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    public static String cutEmail(String email) {
        if (email == null) {
            return null;
        }
        if (email.length() <= 6){
            return email;
        }
        return email.substring(0, 6);
    }
}
