package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Cart;
import kz.epam.entity.Order;
import kz.epam.entity.User;
import kz.epam.message.MessageManager;
import kz.epam.util.OrderNumberGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.DAY_OF_WEEK;

public class ConfirmOrder implements Command {

    private static final String COMMENT = "comment";
    private static final int NUMBER_OF_DAYS = 2;
    private static final String DATE_ERROR = "dateErrorMessage";
    private static final String NULL_DATE = "dateNullMessage";
    private static final String WRONG_DATE_MESSAGE = "error.date.wrong";
    private static final String NULL_DATE_MESSAGE = "error.date.null";
    private static final String PATH_TO_CHECKOUT_PAGE = "/jsp/user/checkout.jsp";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/user/confirmed_order.jsp";

    //    TODO: split the whole method into several small methods;
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Date date = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);
        Cart cart = (Cart) session.getAttribute(Constant.CART);
        String requestedDate = request.getParameter(Constant.DATE);
        String comment = request.getParameter(COMMENT);

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        Date minimumDate = calendar.getTime();

        DateFormat formatter;
        formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            minimumDate = formatter.parse(formatter.format(calendar.getTime()));
            date = formatter.parse(requestedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        OrderDAO orderDAO = new OrderDAO();

        if (date != null) {
            calendar.setTime(date);
            int dayOfWeek = calendar.get(DAY_OF_WEEK);

            if (date.before(minimumDate) || dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY) {
                request.setAttribute(DATE_ERROR,
                        MessageManager.getInstance(locale).getProperty(WRONG_DATE_MESSAGE));
                page = PATH_TO_CHECKOUT_PAGE;

            } else {

                List<Integer> usedNumbers = orderDAO.findAllOrderNumbers();
                String orderNumber = OrderNumberGenerator.generateOrderNumber(orderDAO.findAllOrderNumbers());

                if (!usedNumbers.contains(Integer.parseInt(orderNumber))) {
                    Order order = new Order();
                    order.setOrderNumber(orderNumber);
                    order.setUser(user);
                    order.setItems(cart.getItems());
                    order.setRequestedDate(date);
                    order.setComment(comment);
                    order.setStatus(Constant.IN_PROGRESS_STATUS);
                    orderDAO.create(order);
                    cart.getItems().clear();
                }

                session.setAttribute(Constant.CART, cart);

                page = PATH_TO_CONFIRMATION_PAGE;
            }

        } else {
            request.setAttribute(NULL_DATE,
                    MessageManager.getInstance(locale).getProperty(NULL_DATE_MESSAGE));
            page = PATH_TO_CHECKOUT_PAGE;
        }

        return page;
    }
}
