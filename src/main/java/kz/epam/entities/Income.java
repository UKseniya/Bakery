package kz.epam.entities;

import sun.util.locale.LocaleUtils;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
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

//    public String getMonthName() {
//        return new DateFormatSymbols().getMonths()[this.month-1];
//        return Month.of(this.month).getDisplayName(TextStyle.FULL_STANDALONE, locale);
//        return Month.of(this.month).toString();
//    }

    public String getSumCurrencyFormat()
    {
        Locale locale = new Locale("ru", "KZ");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(this.getSum());
    }
}
