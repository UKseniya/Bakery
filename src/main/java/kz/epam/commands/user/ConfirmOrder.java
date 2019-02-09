package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.Order;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ConfirmOrder implements Command {
    private static int INITIAL_NUMBER = 000001;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Date date = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        String receivedDate = request.getParameter("date");
        String comment = request.getParameter("comment");

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(receivedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        OrderDAO orderDAO = new OrderDAO();

        if (date != null) {

//            Random random = new Random();
            String orderNumber = null;
//            int order_number = random.nextInt(MAXIMUM_ORDERS);
            List<Integer> usedNumbers = orderDAO.findAllOrderNumbers();
            if (usedNumbers.size() != 0) {
                for (int i = 0; i < usedNumbers.size()+1; i++) {
                    if (!usedNumbers.contains(INITIAL_NUMBER)) {
                        orderNumber = String.format("%06d",INITIAL_NUMBER);
                        break;
                    }
                    INITIAL_NUMBER++;
                }
            }
            else {
                orderNumber = String.format("%06d",INITIAL_NUMBER);
            }
//            if (usedNumbers.size() == 0 || !usedNumbers.contains(order_number)) {
            if (!usedNumbers.contains(Integer.parseInt(orderNumber))) {
                Order order = new Order();
                order.setOrderNumber(orderNumber);
                order.setUser(user);
                order.setItems(cart.getItems());
                order.setRequestedDate(date);
                order.setComment(comment);
                order.setStatus("in progress");
                boolean created = orderDAO.create(order);
                cart.getItems().clear();
            }
//            else {
//                while (usedNumbers.contains(order_number)) {
//                    order_number = random.nextInt(MAXIMUM_ORDERS);
//                    if (!usedNumbers.contains(order_number)) {
//                        Order order = new Order();
//                        order.setOrderNumber(order_number);
//                        order.setUser(user);
//                        order.setItems(cart.getItems());
//                        order.setRequestedDate(date);
//                        order.setStatus("in progress");
//                        boolean created = orderDAO.create(order);
//                        break;
//                    }
//                }
//            }

            session.setAttribute("cart", cart);

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
