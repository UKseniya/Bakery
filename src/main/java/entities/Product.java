package entities;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Product extends Entity implements Serializable {
    private String code;
    private String name;
    private double price;

    public Product() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFormatedName() {
        String formatedName = null;
        formatedName = this.name.toLowerCase().replace(" ", "");
        return formatedName;
    }

    public String getPriceCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }
}
