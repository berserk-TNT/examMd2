package glocery.tools;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Validation {

    public static final String USERNAME_REGEX = "^[\\w\\d.-_]{8,26}$";
    public static final String PASSWORD_REGEX = "^[\\w\\d]{8,26}$";
    public static final String NAME_REGEX = "^[\\w ]*$";
    public static final String PHONENUMBER_REGEX = "^((0?)||84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    public static final String EMAIL_REGEX = "^([\\w\\d._]*)+(@[\\w]{3,})+(.[\\w]{2,3})$";

    public static boolean isUsernameValid(String password) {
        return Pattern.compile(USERNAME_REGEX).matcher(password).matches();
    }

    public static boolean isPasswordValid(String password) {
        return Pattern.compile(PASSWORD_REGEX).matcher(password).matches();
    }

    public static boolean isNameValid(String name) {
        return Pattern.compile(NAME_REGEX).matcher(name).matches();
    }

    public static boolean isPhoneValid(String number) {
        return Pattern.compile(PHONENUMBER_REGEX).matcher(number).matches();
    }

    public static boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    private static final String PATTERN_FORMAT = "HH:mm dd-MM-yyyy";

    public static String instantToString(Instant instant) {
        return instantToString(instant, null);
    }

    public static String instantToString(Instant instant, String patternFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternFormat != null ? patternFormat : PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String doubleToVND(Double value) {
        String patternVND = ",###₫";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }
}
