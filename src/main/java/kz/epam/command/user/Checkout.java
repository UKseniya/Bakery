package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Checkout implements Command {

    private static final String PATH_TO_CHECKOUT_PAGE = ConfigManager.getInstance().getProperty("path.page.checkout");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(Constant.CART);

        session.setAttribute(Constant.CART, cart);

        page = PATH_TO_CHECKOUT_PAGE;

        return page;
    }
}
