package kz.epam.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Order extends Entity implements Serializable {
    private User user;
    private LineItem item;
    private Date requestedDate;
    private String status;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public LineItem getItem() {
        return item;
    }

    public void setItem(LineItem item) {
        this.item = item;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
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

//    public double getOrderTotal() {
//        double orderTotal = 0.0;
//        for (LineItem item : items) {
//            orderTotal += item.getProduct().getPrice();
//        }
//        return orderTotal;
//    }

//    public String getOrderTotalCurrencyFormat() {
//        double total = this.getOrderTotal();
//        NumberFormat currency = NumberFormat.getCurrencyInstance();
//        String formattedTotal = currency.format(total);
//        return formattedTotal;
//    }

}
