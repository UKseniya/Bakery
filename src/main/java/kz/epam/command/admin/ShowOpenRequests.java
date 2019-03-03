package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowOpenRequests implements Command {

    private static final String CHANGE_BUTTON = "private static final String CHANGE_BUTTON = \"\";";
    private static final String COMPLETE_BUTTON = "completeButton";
    private static final String CLOSE_BUTTON = "closeButton";
    private static final String RECEIVED_DATE = "date";
    private static final String ORDER_NUMBER = "orderNumber";
    private static final String PATH_TO_REVIEW_ORDERS = "/jsp/admin/review_orders.jsp";

    private List<Order> pendingOrders;
    private List<Order> completedOrders;

    @Override
    public String execute(HttpServletRequest request) {
        Date date = null;
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();

        String changeButton = request.getParameter(CHANGE_BUTTON);
        String completeButton = request.getParameter(COMPLETE_BUTTON);
        String closeButton = request.getParameter(CLOSE_BUTTON);
        String receivedDate = request.getParameter(RECEIVED_DATE);
        String orderNumber = request.getParameter(ORDER_NUMBER);

        OrderDAO orderDAO = new OrderDAO();

        if (completeButton != null) {
            orderDAO.updatePendingOrder(orderNumber);
        }

        if (closeButton != null) {
            orderDAO.updateCompletedOrder(orderNumber);
        }

        Calendar calendar = Calendar.getInstance();
        Date requestCompletionDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date requestProcessingDate = calendar.getTime();

        if (changeButton != null) {
            DateFormat formatter;
            formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
            try {
                date = formatter.parse(receivedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            date = requestProcessingDate;
        }

        java.util.Date utilCurrentDate = date;
        java.sql.Date sqlCurrentDate = new java.sql.Date(utilCurrentDate.getTime());

        pendingOrders = orderDAO.findAllPendingOrdersByDate(sqlCurrentDate, locale);

        session.setAttribute(Constants.PENDING_ORDERS, pendingOrders);

        java.util.Date utilPickupDate = requestCompletionDate;
        java.sql.Date sqlPickUpDate = new java.sql.Date(utilPickupDate.getTime());

        completedOrders = orderDAO.findAllCompletedOrdersByDate(sqlPickUpDate, locale);

        session.setAttribute(Constants.COMPLETE_ORDERS, completedOrders);

        page = PATH_TO_REVIEW_ORDERS;

        return page;
    }
}
