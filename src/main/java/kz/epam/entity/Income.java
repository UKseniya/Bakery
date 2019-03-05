package kz.epam.entity;

import java.text.NumberFormat;
import java.util.Locale;

public class Income extends Entity {

    private double sum;
    private String month;
    private int year;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //    TODO: think about moving all methods from entity to util
    public String getSumCurrencyFormat()
    {
        Locale locale = new Locale("ru", "KZ");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(this.getSum());
    }
}
