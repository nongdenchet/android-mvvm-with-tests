package apidez.com.android_mvvm_sample.util;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class NumericUtils {
    /**
     * Check if a string is Integer
     */
    public static boolean isInteger(String number) {
        return number.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Check if a string is Integer
     */
    public static boolean isInteger(CharSequence number) {
        return isInteger(number.toString());
    }
}
