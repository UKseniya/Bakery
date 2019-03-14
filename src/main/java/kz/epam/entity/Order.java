package kz.epam.entity;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Order extends Entity {

    private String orderNumber;
    private User user;
    private List<LineItem> items;
    private Date requestedDate;
    private String comment;
    private String status;

    public Order() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedDateFormat() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String invoiceDateFormatted = dateFormat.format(requestedDate);
        return invoiceDateFormatted;
    }

    public double getOrderTotal() {
        double orderTotal = 0.0;
        for (LineItem item : items) {
            orderTotal += item.getItemTotal();
        }
        return orderTotal;
    }

    public String getOrderTotalCurrencyFormat() {

        Locale locale = new Locale("ru", "KZ");
        double total = this.getOrderTotal();
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        String formattedTotal = currency.format(total);
        return formattedTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber) &&
                Objects.equals(user, order.user) &&
                Objects.equals(items, order.items) &&
                Objects.equals(requestedDate, order.requestedDate) &&
                Objects.equals(comment, order.comment) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, user, items, requestedDate, comment, status);
    }
}
