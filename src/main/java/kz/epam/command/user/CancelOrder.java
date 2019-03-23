package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;
import kz.epam.message.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CancelOrder implements Command {

    private static final String ORDER_NUMBER = "orderNumber";
    private static final int NUMBER_OF_DAYS = -5;
    private static final String LATE_DATE = "lateDateMessage";
    private static final String LATE_DATE_MESSAGE = "late.date";
    private static final String PATH_TO_UPDATED_ORDER_LIST_PAGE = ConfigManager.getInstance().getProperty("path.command.show.all.orders");
    private static final String PATH_TO_REVIEW_ORDERS_PAGE = ConfigManager.getInstance().getProperty("path.page.review.user.orders");

    private Logger log = Logger.getRootLogger();

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Date date = new Date();

        HttpSession session = request.getSession();

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language.substring(0,2));

        String orderNumber = request.getParameter(ORDER_NUMBER);

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        OrderDAO orderDAO = new OrderDAO();

        Order order = orderDAO.findEntityByOrderNumber(orderNumber);

        Date orderDate = order.getRequestedDate();

        calendar.setTime(orderDate);
        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);

        DateFormat formatter;
        formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            currentDate = formatter.parse(formatter.format(currentDate));
            date = formatter.parse(formatter.format(calendar.getTime()));
        } catch (ParseException e) {
            log.error(e.toString());
        }

        if (date.after(currentDate)) {
            orderDAO.delete(order.getId());
            page = PATH_TO_UPDATED_ORDER_LIST_PAGE;
        } else {
            request.setAttribute(LATE_DATE,
                    MessageManager.getInstance(locale).getProperty(LATE_DATE_MESSAGE));
            page = PATH_TO_REVIEW_ORDERS_PAGE;
        }

        return page;
    }
}
