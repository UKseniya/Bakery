package kz.epam.util;

import java.util.List;

public class OrderNumberGenerator {

    private static final String UTILITY_CLASS_MESSAGE = "Utility Class";
    private static final String ORDER_NUMBER_FORMAT = "%06d";
    private static final int ZERO = 0;
    private static final int INCREMENT = 1;
    private static int initialNumber = 000001;

    private OrderNumberGenerator() {
        throw new IllegalStateException(UTILITY_CLASS_MESSAGE);
    }

    public static String generateOrderNumber(List<Integer> usedNumbers) {
        String number = null;

        if (usedNumbers.size() != ZERO) {
            for (int i = ZERO; i < usedNumbers.size() + INCREMENT; i++) {
                if (!usedNumbers.contains(initialNumber)) {
                    number = String.format(ORDER_NUMBER_FORMAT, initialNumber);
                    break;
                }
                initialNumber++;
            }
        } else {
            number = String.format(ORDER_NUMBER_FORMAT, initialNumber);
        }
        return number;
    }
}
