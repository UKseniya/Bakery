package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.LineItem;
import kz.epam.entity.Order;
import kz.epam.message.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.DAY_OF_WEEK;

public class SelectDate implements Command {

    private static final int MAXIMUM_ORDER_QUANTITY = 10;
    private static final int NUMBER_OF_DAYS = 2;
    private static final String SELECT_DATE_BUTTON = "selectDateButton";
    private static final String DATE_ERROR = "dateErrorMessage";
    private static final String NULL_DATE = "dateNullMessage";
    private static final String WRONG_DATE_MESSAGE = "error.date.wrong";
    private static final String NULL_DATE_MESSAGE = "error.date.null";
    private static final String AVAILABLE_QUANTITY = "availableQuantity";
    private static final String FULL_DATE = "dateFullMessage";
    private static final String FULL_DATE_MESSAGE = "error.date.full";
    private static final String PATH_TO_SELECT_DATE_PAGE = ConfigManager.getInstance().getProperty("path.page.select.date");
    private static final String PATH_TO_SELECTING_ORDER_PAGE = ConfigManager.getInstance().getProperty("path.command.ordering");

    private Logger log = Logger.getRootLogger();

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int totalItemQuantity;

        Date date = null;
        java.sql.Date sqlDate = null;

        HttpSession session = request.getSession();
        String selectDateButton = request.getParameter(SELECT_DATE_BUTTON);
        String requestedDate = request.getParameter(Constant.DATE);

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language.substring(0, 2));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constant.UTC));

        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        Date minimumDate = calendar.getTime();

        if (requestedDate != null && !requestedDate.equals("")) {
            DateFormat formatter;
            formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
            try {
                minimumDate = formatter.parse(formatter.format(calendar.getTime()));
                date = formatter.parse(requestedDate);
            } catch (ParseException e) {
                log.error(e.toString());
            }

            java.util.Date utilDate = date;
            sqlDate = new java.sql.Date(utilDate.getTime());
        } else {
            page = PATH_TO_SELECT_DATE_PAGE;
        }
        OrderDAO orderDAO = new OrderDAO();

        if (selectDateButton != null) {
            if (!requestedDate.equals("")) {
                calendar.setTime(date);
                int dayOfWeek = calendar.get(DAY_OF_WEEK);

                boolean possibleDate = date.before(minimumDate);

                if (date.before(minimumDate) || (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY)) {
                    request.setAttribute(DATE_ERROR,
                            MessageManager.getInstance(locale).getProperty(WRONG_DATE_MESSAGE));
                    page = PATH_TO_SELECT_DATE_PAGE;
                } else {
                    List<Order> orders = orderDAO.findAllPendingOrdersByDate(sqlDate, language);
                    totalItemQuantity = getItemTotal(orders);

                    if (totalItemQuantity < MAXIMUM_ORDER_QUANTITY) {
                        int availableQuantity = getAvailableQuantity(totalItemQuantity);
                        session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
                        session.setAttribute(Constant.DATE, date);
                        page = PATH_TO_SELECTING_ORDER_PAGE;
                    } else {
                        request.setAttribute(FULL_DATE,
                                MessageManager.getInstance(locale).getProperty(FULL_DATE_MESSAGE));
                        page = PATH_TO_SELECT_DATE_PAGE;
                    }
                }
            } else {
                request.setAttribute(NULL_DATE,
                        MessageManager.getInstance(locale).getProperty(NULL_DATE_MESSAGE));
                page = PATH_TO_SELECT_DATE_PAGE;
            }
        }

        return page;
    }

    private static int getItemTotal(List<Order> orders) {
        int totalItems = 0;

        for (Order order : orders) {
            for (LineItem item : order.getItems()) {
                totalItems = totalItems + item.getQuantity();
            }
        }
        return totalItems;
    }

    private static int getAvailableQuantity(int total) {

        int availableQuantity = MAXIMUM_ORDER_QUANTITY - total;

        if (availableQuantity > 5) {
            availableQuantity = 5;
        }

        return availableQuantity;
    }
}
