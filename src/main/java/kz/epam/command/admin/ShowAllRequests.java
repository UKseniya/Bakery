package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllRequests implements Command {

    private static final String ALL_ORDERS = "allOrders";
    private static final String PATH_TO_REVIEW_ALL_ORDERS = ConfigManager.getInstance().getProperty("path.page.review.all.orders");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString();

        OrderDAO orderDAO = new OrderDAO();

        List<Order> allOrders = orderDAO.findAll(locale);

        session.setAttribute(ALL_ORDERS, allOrders);

        page = PATH_TO_REVIEW_ALL_ORDERS;

        return page;
    }
}
