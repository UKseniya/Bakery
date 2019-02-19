package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllOrders implements Command {
    private static final String PATH_TO_REVIEW_ORDERS_PAGE = "/jsp/user/review_orders.jsp";
    private List<Order> pendingOrders;
    private List<Order> completedOrders;
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

        page = PATH_TO_REVIEW_ORDERS_PAGE;

        return page;
    }
}
