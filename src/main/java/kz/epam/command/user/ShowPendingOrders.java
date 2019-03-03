package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowPendingOrders implements Command {

    private static final String ORDERS = "orders";
    private static final String PATH_TO_REVIEW_ORDERS_PAGE = "/jsp/user/review_orders.jsp";
    private List<Order> orders;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();
        User user = (User) session.getAttribute(Constants.USER);

        OrderDAO orderDAO = new OrderDAO();
        orders = orderDAO.findAllPendingOrdersByUser(user, locale);

        session.setAttribute(ORDERS, orders);

        page = PATH_TO_REVIEW_ORDERS_PAGE;

        return page;
    }
}
