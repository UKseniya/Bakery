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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;

public class SelectDate implements Command {

    private static final Logger LOG = Logger.getRootLogger();
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

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        Date date = null;

        HttpSession session = request.getSession();
        String selectDateButton = request.getParameter(SELECT_DATE_BUTTON);
        String requestedDate = request.getParameter(Constant.DATE);

        String language = session.getAttribute(Constant.LOCALE).toString();
        Locale locale = new Locale(language.substring(0, 2));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        Date minimumDate = calendar.getTime();

        if (requestedDate != null && !requestedDate.isEmpty()) {
            DateFormat formatter;
            formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
            try {
                minimumDate = formatter.parse(formatter.format(calendar.getTime()));
                date = formatter.parse(requestedDate);
                calendar.setTime(date);
            } catch (ParseException e) {
                LOG.error(String.format(Constant.STRING_FORMAT, Constant.DATE_FORMAT_PARCING_ERROR_MESSAGE, e.toString()));
            }
        } else {
            page = PATH_TO_SELECT_DATE_PAGE;
        }

        if (selectDateButton != null) {
            if (requestedDate.isEmpty()) {
                request.setAttribute(NULL_DATE,
                        MessageManager.getInstance(locale).getProperty(NULL_DATE_MESSAGE));
                page = PATH_TO_SELECT_DATE_PAGE;
            } else if (date.before(minimumDate) || (calendar.get(DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(DAY_OF_WEEK) == Calendar.MONDAY)) {
                request.setAttribute(DATE_ERROR,
                        MessageManager.getInstance(locale).getProperty(WRONG_DATE_MESSAGE));
                page = PATH_TO_SELECT_DATE_PAGE;
            } else if (getItemTotal(date, locale) >= MAXIMUM_ORDER_QUANTITY) {
                request.setAttribute(FULL_DATE,
                        MessageManager.getInstance(locale).getProperty(FULL_DATE_MESSAGE));
                page = PATH_TO_SELECT_DATE_PAGE;
            } else {
                int availableQuantity = getAvailableQuantity(getItemTotal(date, locale));
                session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
                session.setAttribute(Constant.DATE, date);
                page = PATH_TO_SELECTING_ORDER_PAGE;
            }
        }
        return page;
    }

    private static java.sql.Date getSQLDate(Date date) {
        java.util.Date utilDate = date;
        return new java.sql.Date(utilDate.getTime());
    }

    private static int getItemTotal(Date date, Locale locale) {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.findAllPendingOrdersByDate(getSQLDate(date), locale.toString());
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
