package kz.epam.commands.user;

import com.sun.org.apache.xpath.internal.operations.Or;
import kz.epam.commands.Command;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllOrders implements Command {
    private List<Order> pendingOrders;
    private List<Order> completedOrders;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        OrderDAO orderDAO = new OrderDAO();

        pendingOrders = orderDAO.findAllPendingOrdersByUser(user);

        session.setAttribute("pendingOrders", pendingOrders);

//        completedOrders = orderDAO.findAllOrdersByUser(user);
//
//        session.setAttribute("orders", completedOrders);

        page = "/jsp/user/review_orders.jsp";

        return page;
    }
}
