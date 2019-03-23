package kz.epam.entity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class LineItem extends Entity {

    private Product product;
    private int quantity;

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

    public double getItemTotal() {
        return product.getPrice() * quantity;
    }

    public String getTotalCurrencyFormat() {
        Locale locale = new Locale("ru", "KZ");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(this.getItemTotal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return quantity == lineItem.quantity &&
                Objects.equals(product, lineItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

}
