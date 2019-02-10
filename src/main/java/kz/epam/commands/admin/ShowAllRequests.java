package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.dao.OrderDAO;
import kz.epam.entities.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowAllRequests implements Command {
    private List<Order> allOrders;
    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();

        OrderDAO orderDAO = new OrderDAO();

        allOrders = orderDAO.findAll();

        session.setAttribute("allOrders", allOrders);

        page = "/jsp/admin/review_all_orders.jsp";

        return page;
    }
}
