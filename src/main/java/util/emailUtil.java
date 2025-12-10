package util;

public class emailUtil {

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
}
