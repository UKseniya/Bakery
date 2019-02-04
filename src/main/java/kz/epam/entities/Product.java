package kz.epam.entities;

import kz.epam.constants.Constants;
import sun.util.locale.LocaleUtils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public String getFormattedName() {
        String formattedname = null;
        formattedname = this.name.toLowerCase().replace(" ", "");
        return formattedname;
    }

    public String getPriceCurrencyFormat() {

        Locale locale = new Locale("ru", "KZ");

//        NumberFormat formater = NumberFormat.getNumberInstance();
//        return formater.format(price);

        NumberFormat formater = NumberFormat.getCurrencyInstance(locale);
        return formater.format(price);

    }


}
