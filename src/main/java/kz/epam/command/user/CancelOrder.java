package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CancelOrder implements Command {

    private static final String ORDER_ID = "orderID";
    private static final int NUMBER_OF_DAYS = 5;
    private static final String LATE_DATE = "lateDateMessage";
    private static final String LATE_DATE_MESSAGE = "late.date";
    private static final String PATH_TO_UPDATED_ORDER_LIST_PAGE = "/controller?command=show_all_orders";
    private static final String PATH_TO_REVIEW_ORDERS_PAGE = "/jsp/user/review_orders.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Date date = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        String orderID = request.getParameter(ORDER_ID);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        //TODO: delete function

//        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
//        Date minimumDateToCancel = calendar.getTime();

        OrderDAO orderDAO = new OrderDAO();

        Order order = orderDAO.findEntityById(Integer.parseInt(orderID));

        Date orderDate = order.getRequestedDate();

        calendar.setTime(orderDate);
        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);

        DateFormat formatter;
        formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            currentDate = formatter.parse(formatter.format(calendar.getTime()));
            date = formatter.parse(formatter.format(orderDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (orderDate.before(currentDate)) {
            orderDAO.delete(order.getId());
        }
        else {
            request.setAttribute(LATE_DATE,
                    MessageManager.getInstance(locale).getProperty(LATE_DATE_MESSAGE));
            page = PATH_TO_REVIEW_ORDERS_PAGE;
        }

        return PATH_TO_UPDATED_ORDER_LIST_PAGE;
    }
}
