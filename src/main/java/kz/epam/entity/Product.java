package kz.epam.entity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

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

    public String getFormattedCode() {
        String formattedCode = null;
        formattedCode = this.code.substring(0, 3);
        return formattedCode;
    }

    public String getPriceCurrencyFormat() {

        Locale locale = new Locale("ru", "KZ");

        NumberFormat formater = NumberFormat.getCurrencyInstance(locale);
        return formater.format(price);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(code, product.code) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(status, product.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, price, status);
    }
}
