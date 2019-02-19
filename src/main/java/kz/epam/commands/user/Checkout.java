package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.entities.Cart;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Checkout implements Command {
    private static final String PATH_TO_CHECKOUT_PAGE = "/jsp/user/checkout.jsp";
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        Cart cart = (Cart) session.getAttribute(Constants.CART);

        page = PATH_TO_CHECKOUT_PAGE;

        return page;
    }
}
