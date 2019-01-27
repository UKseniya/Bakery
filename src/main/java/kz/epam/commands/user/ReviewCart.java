package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.entities.Cart;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReviewCart implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Cart cart = (Cart) session.getAttribute("cart");

//        List<LineItem> items = cart.getItems();
//        double total = 0.0;
//
//        for (LineItem item : items) {
//            total += item.getTotal();
//        }

        page = "/jsp/user/review_cart.jsp";

        return page;
    }
}
