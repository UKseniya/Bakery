package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Cart;
import kz.epam.entity.LineItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReviewCart implements Command {

    private static final String PATH_TO_REVIEW_CART_PAGE = ConfigManager.getInstance().getProperty("path.page.cart.review");

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0,2);

        Cart cart = (Cart) session.getAttribute(Constant.CART);

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
        session.setAttribute(Constant.CART, cart);

        page = PATH_TO_REVIEW_CART_PAGE;

        return page;
    }
}
