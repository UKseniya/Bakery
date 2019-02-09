package kz.epam.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Order extends Entity implements Serializable {
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

    //
//    public Date getDate() {
//        return requestedDate;
//    }
//
//    public void setDate(Date date) {
//        this.requestedDate = date;
//    }

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

}
