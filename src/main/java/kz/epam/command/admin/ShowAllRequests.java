package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllRequests implements Command {

    private static final String ALL_ORDERS = "allOrders";
    private static final String PATH_TO_REVIEW_ALL_ORDERS = "/jsp/admin/review_all_orders.jsp";

    private List<Order> allOrders;

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString();

        OrderDAO orderDAO = new OrderDAO();

        allOrders = orderDAO.findAll(locale);

        session.setAttribute(ALL_ORDERS, allOrders);

        page = PATH_TO_REVIEW_ALL_ORDERS;

        return page;
    }
}
