package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;
import kz.epam.entity.User;

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
        String locale = session.getAttribute(Constant.LOCALE).toString();
        User user = (User) session.getAttribute(Constant.USER);

        OrderDAO orderDAO = new OrderDAO();
        orders = orderDAO.findAllPendingOrdersByUser(user, locale);

        session.setAttribute(ORDERS, orders);

        page = PATH_TO_REVIEW_ORDERS_PAGE;

        return page;
    }
}
