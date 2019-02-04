package kz.epam.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumber {
    private int number;

    public int getRandomNumber () {
        int randomNumber = 0;
        Random random = new Random();
        randomNumber = random.nextInt();
        return randomNumber;
    }
}
