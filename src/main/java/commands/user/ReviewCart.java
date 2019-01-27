package commands.user;

import commands.Command;
import entities.Cart;
import entities.LineItem;
import entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReviewCart implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Cart cart = (Cart) session.getAttribute("cart");

        List<LineItem> items = cart.getItems();
        double total = 0.0;

        for (LineItem item : items) {
            total += item.getTotal();
        }

        page = "/jsp/user/review_cart.jsp";

        return page;
    }
}
