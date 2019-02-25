package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.Order;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

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
    private static final int NULL = 0;
    private static final int INCREMENT = 1;
    private static final String ORDER_NUMBER_FORMAT = "%06d";
    private static final String DATE_ERROR = "dateErrorMessage";
    private static final String NULL_DATE = "dateNullMessage";
    private static final String WRONG_DATE_MESSAGE = "error.date.wrong";
    private static final String NULL_DATE_MESSAGE = "error.date.null";
    private static final String PATH_TO_CHECKOUT_PAGE = "/jsp/user/checkout.jsp";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/user/confirmed_order.jsp";
    private static int INITIAL_NUMBER = 000001;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Date date = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        Cart cart = (Cart) session.getAttribute(Constants.CART);
        String requestedDate = request.getParameter(Constants.DATE);
        String comment = request.getParameter(COMMENT);

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        Date minimumDate = calendar.getTime();

        DateFormat formatter;
        formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
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

            }
            else {
                String orderNumber = null;
                List<Integer> usedNumbers = orderDAO.findAllOrderNumbers();
                if (usedNumbers.size() != NULL) {
                    for (int i = NULL; i < usedNumbers.size()+INCREMENT; i++) {
                        if (!usedNumbers.contains(INITIAL_NUMBER)) {
                            orderNumber = String.format(ORDER_NUMBER_FORMAT,INITIAL_NUMBER);
                            break;
                        }
                        INITIAL_NUMBER++;
                    }
                }
                else {
                    orderNumber = String.format(ORDER_NUMBER_FORMAT,INITIAL_NUMBER);
                }

                if (!usedNumbers.contains(Integer.parseInt(orderNumber))) {
                    Order order = new Order();
                    order.setOrderNumber(orderNumber);
                    order.setUser(user);
                    order.setItems(cart.getItems());
                    order.setRequestedDate(date);
                    order.setComment(comment);
                    order.setStatus(Constants.IN_PROGRESS_STATUS);
                    orderDAO.create(order);
                    cart.getItems().clear();
                }

                session.setAttribute(Constants.CART, cart);

                page = PATH_TO_CONFIRMATION_PAGE;
            }

        }
        else {
            request.setAttribute(NULL_DATE,
                    MessageManager.getInstance(locale).getProperty(NULL_DATE_MESSAGE));
            page = PATH_TO_CHECKOUT_PAGE;
        }

        return page;
    }
}
