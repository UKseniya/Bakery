package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.Product;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddToCart implements Command {
    private static final String PATH_TO_SELECTING_ORDER_PAGE = "/jsp/user/make_order.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int itemQuantity = 1;

        String productCode = request.getParameter(Constants.PRODUCT_CODE);

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();
        User user = (User) session.getAttribute(Constants.USER);

        Cart cart = (Cart) session.getAttribute(Constants.CART);
        if (cart == null)
        {
            cart = new Cart();
            session.setAttribute(Constants.CART, cart);
        }

        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        product = productDAO.findProductbyCode(productCode, locale);
        session.setAttribute(Constants.PRODUCT, product);

        if (product != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(itemQuantity);
                cart.addItem(lineItem);
        }

        session.setAttribute(Constants.CART, cart);

        page = PATH_TO_SELECTING_ORDER_PAGE;

        return page;
    }
}
