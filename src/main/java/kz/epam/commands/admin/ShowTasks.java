package kz.epam.commands.admin;

import kz.epam.commands.Command;
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

public class ShowTasks implements Command {
    private List<Order> pendingOrders;
    private List<Order> completedOrders;

    @Override
    public String execute(HttpServletRequest request) {
        Date date = null;
        String page;

        HttpSession session = request.getSession();

        String changeButton = request.getParameter("changeDateButton");
        String completeButton = request.getParameter("completeButton");
        String closeButton = request.getParameter("closeButton");
        String receivedDate = request.getParameter("date");
        String orderNumber = request.getParameter("orderNumber");

        OrderDAO orderDAO = new OrderDAO();

        if (completeButton != null) {
            orderDAO.updatePendingOrder(orderNumber);
        }

        if (closeButton != null) {
            orderDAO.updateCompletedOrder(orderNumber);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date currentDate = calendar.getTime();

        if (changeButton != null) {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = formatter.parse(receivedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            date = currentDate;
        }

        java.util.Date utilCurrentDate = date;
        java.sql.Date sqlCurrentDate = new java.sql.Date(utilCurrentDate.getTime());

        pendingOrders = orderDAO.findAllPendingOrdersByDate(sqlCurrentDate);

        session.setAttribute("pendingOrders", pendingOrders);

        java.util.Date utilPickupDate = currentDate;
        java.sql.Date sqlPickUpDate = new java.sql.Date(utilPickupDate.getTime());

        completedOrders = orderDAO.findAllCompletedOrdersByDate(sqlPickUpDate);

        session.setAttribute("completedOrders", completedOrders);

        page = "/jsp/admin/review_orders.jsp";

        return page;
    }
}
