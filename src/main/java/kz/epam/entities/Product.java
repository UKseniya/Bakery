package kz.epam.entities;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Product extends Entity {

    private String code;
    private String name;
    private String description;
    private double price;
    private String status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    TODO: think about moving all methods from entity to util
    public String getFormattedCode() {
        String formattedCode = null;
        formattedCode = this.code.substring(0,3);
        return formattedCode;
    }

    public String getPriceCurrencyFormat() {

        Locale locale = new Locale("ru", "KZ");

        NumberFormat formater = NumberFormat.getCurrencyInstance(locale);
        return formater.format(price);

    }


}
