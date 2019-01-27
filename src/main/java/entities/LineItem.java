package entities;

import java.io.Serializable;
import java.text.NumberFormat;

public class LineItem extends Entity implements Serializable {

    private Product product;
    private int quantity;
    private double total;

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

    public double getTotal()
    {
        double total = product.getPrice() * quantity;
        return total;
    }

    public String getTotalCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
}
