package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowOpenRequests implements Command {

    private static final String CHANGE_BUTTON = "changeDateButton";
    private static final String COMPLETE_BUTTON = "completeButton";
    private static final String CLOSE_BUTTON = "closeButton";
    private static final String RECEIVED_DATE = "date";
    private static final int NUMBER_OF_DAYS = 1;
    private static final String ORDER_NUMBER = "orderNumber";
    private static final String PATH_TO_REVIEW_ORDERS = "/jsp/admin/review_orders.jsp";

    private static Date processingDate;
    private static Date completionDate;

    private List<Order> pendingOrders;
    private List<Order> completedOrders;

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString();

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
        calendar.add(Calendar.DAY_OF_YEAR, NUMBER_OF_DAYS);
        Date requestProcessingDate = calendar.getTime();

        if (changeButton != null) {
            DateFormat formatter;
            formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
            try {
                processingDate = formatter.parse(receivedDate);
                completionDate = processingDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (processingDate == null || completionDate == null){
            processingDate = requestProcessingDate;
            completionDate = requestCompletionDate;
        }

        java.util.Date utilCurrentDate = processingDate;
        java.sql.Date sqlCurrentDate = new java.sql.Date(utilCurrentDate.getTime());

        pendingOrders = orderDAO.findAllPendingOrdersByDate(sqlCurrentDate, locale);

        session.setAttribute(Constant.PENDING_ORDERS, pendingOrders);

        java.util.Date utilPickupDate = completionDate;
        java.sql.Date sqlPickUpDate = new java.sql.Date(utilPickupDate.getTime());

        completedOrders = orderDAO.findAllCompletedOrdersByDate(sqlPickUpDate, locale);

        session.setAttribute(Constant.COMPLETE_ORDERS, completedOrders);

        page = PATH_TO_REVIEW_ORDERS;

        return page;
    }
}
