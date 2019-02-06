package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowPendingOrders implements Command {
    private List<Order> orders;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new OrderDAO();
        orders = orderDAO.findAllPendingOrdersByUser(user);

        session.setAttribute("orders", orders);

        page = "/jsp/user/review_orders.jsp";

        return page;
    }
}
