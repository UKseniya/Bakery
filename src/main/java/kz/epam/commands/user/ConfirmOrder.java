package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.Order;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;
import sun.util.locale.LocaleUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.DAY_OF_WEEK;

public class ConfirmOrder implements Command {
    private static int INITIAL_NUMBER = 000001;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Date date = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        String requestedDate = request.getParameter("date");
        String comment = request.getParameter("comment");

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(requestedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date minimumDate = calendar.getTime();

        calendar.setTime(date);

        OrderDAO orderDAO = new OrderDAO();

        if (calendar != null) {
//            calendar.setFirstDayOfWeek(2);
            int dayOfWeek = calendar.get(DAY_OF_WEEK);

//            int firstDayOfWeek = calendar.getFirstDayOfWeek();

            if (calendar.before(minimumDate) || dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {
                request.setAttribute("dateErrorMessage",
                        MessageManager.getInstance(locale).getProperty("error.date.wrong"));
                page = "/jsp/user/checkout.jsp";

            }
            else {
                String orderNumber = null;
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

                session.setAttribute("cart", cart);

                page = "/jsp/user/confirmed_order.jsp";
            }

        }
        else {
            request.setAttribute("dateNullMessage",
                    MessageManager.getInstance(locale).getProperty("error.date.null"));
            page = "/jsp/user/checkout.jsp";
        }

        return page;
    }
}
