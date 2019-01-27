package kz.epam.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

public class Order extends Entity implements Serializable {
    private User user;
    private List<LineItem> items;
    private Date requestedDate;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public Date getDate() {
        return requestedDate;
    }

    public void setDate(Date date) {
        this.requestedDate = date;
    }

    public String getRequestedDateFormat() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String invoiceDateFormatted = dateFormat.format(requestedDate);
        return invoiceDateFormatted;
    }

    public double getOrderTotal() {
        double orderTotal = 0.0;
        for (LineItem item : items) {
            orderTotal += item.getProduct().getPrice();
        }
        return orderTotal;
    }

    public String getOrderTotalCurrencyFormat() {
        double total = this.getOrderTotal();
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String formattedTotal = currency.format(total);
        return formattedTotal;
    }

}
