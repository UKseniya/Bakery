package kz.epam.entity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Cart extends Entity {

    private List<LineItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public double getTotal() {

        double total = 0.00;
        for (int i = 0; i < items.size(); i++) {
            LineItem item = items.get(i);
            total += item.getItemTotal();
        }
        return total;
    }

    public String getTotalCurrencyFormat() {

        Locale locale = new Locale("ru", "KZ");
        double total = this.getTotal();
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(total);
    }

    public void addItem(LineItem item) {
        //If the item already exists in the cart, only the quantity is changed.
        String code = item.getProduct().getCode();
        int quantity = item.getQuantity();
        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getCode().equals(code)) {
                quantity++;
                lineItem.setQuantity(quantity);
                return;
            }
        }
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(items, cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
