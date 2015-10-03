package apidez.com.android_mvvm_sample.utils;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class NumericUtils {
    /**
     * Check if a string is Integer
     */
    public static boolean isNumeric(String number) {
        return number.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Check if a string is Integer
     */
    public static boolean isNumeric(CharSequence number) {
        return isNumeric(number.toString());
    }
}
