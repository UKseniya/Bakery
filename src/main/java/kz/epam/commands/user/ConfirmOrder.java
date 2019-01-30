package kz.epam.commands.user;

import com.sun.org.apache.xpath.internal.operations.Or;
import kz.epam.commands.Command;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.Order;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmOrder implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Date date = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        String receivedDate = request.getParameter("date");

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(receivedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        OrderDAO orderDAO = new OrderDAO();

        if (date != null) {
            for (LineItem item : cart.getItems()) {
                Order order = new Order();
                order.setUser(user);
                order.setItem(item);
                order.setRequestedDate(date);
                boolean created = orderDAO.create(order);
            }
            page = "/jsp/user/confirmed_order.jsp";
        }
        else {
            request.setAttribute("dateErrorMessage",
                    MessageManager.getInstance().getProperty("date.error"));
            page = "/jsp/user/checkout.jsp";
        }

        return page;
    }
}
