package kz.epam.util;

import java.util.List;

public class OrderNumberGenerator {

    private static final String ORDER_NUMBER_FORMAT = "%06d";
    private static final int NULL = 0;
    private static final int INCREMENT = 1;
    private static int INITIAL_NUMBER = 000001;

    public static String generateOrderNumber(List<Integer> usedNumbers) {
        String number = null;

        if (usedNumbers.size() != NULL) {
            for (int i = NULL; i < usedNumbers.size() + INCREMENT; i++) {
                if (!usedNumbers.contains(INITIAL_NUMBER)) {
                    number = String.format(ORDER_NUMBER_FORMAT, INITIAL_NUMBER);
                    break;
                }
                INITIAL_NUMBER++;
            }
        } else {
            number = String.format(ORDER_NUMBER_FORMAT, INITIAL_NUMBER);
        }
        return number;
    }
}
