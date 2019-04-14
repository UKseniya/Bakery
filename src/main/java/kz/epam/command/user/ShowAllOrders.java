package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;
import kz.epam.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowAllOrders implements Command {

    private static final String PATH_TO_REVIEW_ORDERS_PAGE = ConfigManager.getInstance().getProperty("path.page.review.user.orders");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0,2);
        User user = (User) session.getAttribute(Constant.USER);

        OrderDAO orderDAO = new OrderDAO();

        List<Order> pendingOrders = orderDAO.findAllPendingOrdersByUser(user, locale);
        session.setAttribute(Constant.PENDING_ORDERS, pendingOrders);

        List<Order> completedOrders = orderDAO.findAllOrdersByUser(user, locale);
        session.setAttribute(Constant.COMPLETE_ORDERS, completedOrders);

        List<Order> allOrders = new ArrayList<>();
        allOrders.addAll(pendingOrders);
        allOrders.addAll(completedOrders);
        session.setAttribute(Constant.ALL_ORDERS, allOrders);

        page = PATH_TO_REVIEW_ORDERS_PAGE;

        return page;
    }
}
