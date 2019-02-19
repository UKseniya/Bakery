package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReviewCart implements Command {
    private static final String PATH_TO_REVIEW_CART_PAGE = "/jsp/user/review_cart.jsp";
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();
        User user = (User) session.getAttribute(Constants.USER);

        Cart cart = (Cart) session.getAttribute(Constants.CART);

        if (cart != null) {
            Cart reviewCart = new Cart();

            ProductDAO productDAO = new ProductDAO();

            for (LineItem item : cart.getItems()) {
                int productID = productDAO.findProductIdByCode(item.getProduct());
                item.getProduct().setName(productDAO.findProductNameById(productID, locale));
                item.getQuantity();
                reviewCart.addItem(item);
            }
            cart = reviewCart;
        }

        session.setAttribute(Constants.CART, cart);

        page = PATH_TO_REVIEW_CART_PAGE;

        return page;
    }
}
