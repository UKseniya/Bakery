package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;
import kz.epam.entity.User;

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
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString();
        User user = (User) session.getAttribute(Constant.USER);

        OrderDAO orderDAO = new OrderDAO();

        pendingOrders = orderDAO.findAllPendingOrdersByUser(user, locale);

        session.setAttribute(Constant.PENDING_ORDERS, pendingOrders);

        completedOrders = orderDAO.findAllOrdersByUser(user, locale);

        session.setAttribute(Constant.COMPLETE_ORDERS, completedOrders);

        allOrders.addAll(pendingOrders);
        allOrders.addAll(completedOrders);

        session.setAttribute(ALL_ORDERS, allOrders);

        page = PATH_TO_REVIEW_ORDERS_PAGE;

        return page;
    }
}
