package kz.epam.entities;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class LineItem extends Entity {

    private Product product;
    private int quantity;

    public LineItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //    TODO: think about moving all methods from entity to util
    public double getItemTotal()
    {
        double total = product.getPrice() * quantity;
        return total;
    }

    public String getTotalCurrencyFormat()
    {
        Locale locale = new Locale("ru", "KZ");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(this.getItemTotal());
    }
}
