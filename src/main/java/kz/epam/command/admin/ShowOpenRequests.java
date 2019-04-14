package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ShowOpenRequests implements Command {

    private static final Logger LOG = Logger.getRootLogger();
    private static final String CHANGE_DATE_BUTTON = "changeButton";
    private static final String COMPLETE_BUTTON = "completeButton";
    private static final String CLOSE_BUTTON = "closeButton";
    private static final int NUMBER_OF_DAYS = 1;
    private static final String PATH_TO_REVIEW_ORDERS = ConfigManager.getInstance().getProperty("path.page.review.admin.orders");

    private static Date requestedDate;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Date processingDate;
        Date completionDate;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0, 2);

        String changeDateButton = request.getParameter(CHANGE_DATE_BUTTON);
        String completeRequestButton = request.getParameter(COMPLETE_BUTTON);
        String closeRequestButton = request.getParameter(CLOSE_BUTTON);
        String receivedDate = request.getParameter(Constant.DATE);
        String orderNumber = request.getParameter(Constant.ORDER_NUMBER);

        OrderDAO orderDAO = new OrderDAO();

        if (completeRequestButton != null) {
            orderDAO.updatePendingOrder(orderNumber);
        }

        if (closeRequestButton != null) {
            orderDAO.updateCompletedOrder(orderNumber);
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constant.UTC));
        Date requestCompletionDate = calendar.getTime();
        Date requestProcessingDate = getProcessingDate(requestCompletionDate);

        if (changeDateButton != null) {
            completionDate = getRequestedDate(receivedDate);
            processingDate = getProcessingDate(completionDate);
        } else if (requestedDate != null) {
            completionDate = requestedDate;
            processingDate = getProcessingDate(completionDate);
        } else {
            processingDate = requestProcessingDate;
            completionDate = requestCompletionDate;
        }

        List<Order> pendingOrders = orderDAO.findAllPendingOrdersByDate(getSQLDate(processingDate), locale);
        session.setAttribute(Constant.PENDING_ORDERS, pendingOrders);

        List<Order> completedOrders = orderDAO.findAllCompletedOrdersByDate(getSQLDate(completionDate), locale);
        session.setAttribute(Constant.COMPLETE_ORDERS, completedOrders);

        page = PATH_TO_REVIEW_ORDERS;

        return page;
    }

    private static Date getProcessingDate(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(Constant.UTC));
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        return calendar.getTime();
    }

    private static Date getRequestedDate(String date) {
        DateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            requestedDate = formatter.parse(date);
        } catch (ParseException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.DATE_FORMAT_PARCING_ERROR_MESSAGE, e.toString()));
        }
        return requestedDate;
    }

    private static java.sql.Date getSQLDate(Date date) {
        java.util.Date utilDate = date;
        return new java.sql.Date(utilDate.getTime());
    }
}
