package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowAllOrders implements Command {

    private static final String ALL_ORDERS = "allOrders";
    private static final String PATH_TO_REVIEW_ORDERS_PAGE = "/jsp/user/review_orders.jsp";
    private List<Order> pendingOrders;
    private List<Order> completedOrders;
    private List<Order> allOrders = new ArrayList<>();

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();
        User user = (User) session.getAttribute(Constants.USER);

        OrderDAO orderDAO = new OrderDAO();

        pendingOrders = orderDAO.findAllPendingOrdersByUser(user, locale);

        session.setAttribute(Constants.PENDING_ORDERS, pendingOrders);

        completedOrders = orderDAO.findAllOrdersByUser(user, locale);

        session.setAttribute(Constants.COMPLETE_ORDERS, completedOrders);

        allOrders.addAll(pendingOrders);
        allOrders.addAll(completedOrders);

        session.setAttribute(ALL_ORDERS, allOrders);

        page = PATH_TO_REVIEW_ORDERS_PAGE;

        return page;
    }
}
