package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.OrderDAO;
import kz.epam.entity.Cart;
import kz.epam.entity.Order;
import kz.epam.entity.User;
import kz.epam.util.OrderNumberGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class ConfirmOrder implements Command {

    private static final String COMMENT = "comment";
    private static final String PATH_TO_CONFIRMATION_PAGE = ConfigManager.getInstance().getProperty("path.page.order.confirmation");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);
        Cart cart = (Cart) session.getAttribute(Constant.CART);
        String comment = request.getParameter(COMMENT);

        Date date = (Date) session.getAttribute(Constant.DATE);

        OrderDAO orderDAO = new OrderDAO();

        List<Integer> usedNumbers = orderDAO.findAllOrderNumbers();
        String orderNumber = OrderNumberGenerator.generateOrderNumber(orderDAO.findAllOrderNumbers());

        if (!usedNumbers.contains(Integer.parseInt(orderNumber))) {
            Order order = new Order();
            order.setOrderNumber(orderNumber);
            order.setUser(user);
            order.setItems(cart.getItems());
            order.setRequestedDate(date);
            order.setComment(comment);
            order.setStatus(Constant.IN_PROGRESS_STATUS);
            orderDAO.create(order);
            cart.getItems().clear();
        }

        session.setAttribute(Constant.CART, cart);

        page = PATH_TO_CONFIRMATION_PAGE;

        return page;
    }
}
