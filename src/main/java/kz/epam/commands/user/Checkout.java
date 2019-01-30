package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.entities.Cart;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Checkout implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        page = "/jsp/user/checkout.jsp";

        return page;
    }
}
