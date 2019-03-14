package kz.epam.entity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

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

    public String getSumCurrencyFormat() {
        Locale locale = new Locale("ru", "KZ");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(this.getSum());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return Double.compare(income.sum, sum) == 0 &&
                year == income.year &&
                Objects.equals(month, income.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, month, year);
    }
}
