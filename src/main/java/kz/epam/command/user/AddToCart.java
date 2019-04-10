package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Cart;
import kz.epam.entity.LineItem;
import kz.epam.entity.Product;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Locale;

public class AddToCart implements Command {

    private static final String QUANTITY = "quantity";
    private static final String ORDER_ERROR = "orderErrorMessage";
    private static final String ORDER_ERROR_MESSAGE = "error.order.full";
    private static final String PATH_TO_SELECTING_ORDER_PAGE = ConfigManager.getInstance().getProperty("path.page.ordering");
    private static final String PATH_TO_CART_PAGE = ConfigManager.getInstance().getProperty("path.page.cart.review");
    private static final String AVAILABLE_QUANTITY = "availableQuantity";

    private static int totalItemQuantity = 0;
    private static int maximumOrderQuantity = 0;
    private static int availableQuantity;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int itemQuantity = 1;

        String productCode = request.getParameter(Constant.PRODUCT_CODE);
        String receivedQuantity = request.getParameter(QUANTITY);
        String selectButton = request.getParameter(Constant.SELECT_BUTTON);
        String addButton = request.getParameter(Constant.ADD_BUTTON);
        String removeButton = request.getParameter(Constant.REMOVE_BUTTON);

        HttpSession session = request.getSession();

        String quantity = request.getParameter(AVAILABLE_QUANTITY);

        if (maximumOrderQuantity == 0) {
            maximumOrderQuantity = Integer.parseInt(quantity);
        }
        if (quantity != null) {
            availableQuantity = Integer.parseInt(quantity);
        }

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language.substring(0,2));

        Cart cart = (Cart) session.getAttribute(Constant.CART);
        if (cart == null) {
            cart = new Cart();
            totalItemQuantity = 0;
            session.setAttribute(Constant.CART, cart);
        }

        if (addButton != null) {
            itemQuantity = Integer.parseInt(receivedQuantity);

            if (totalItemQuantity < maximumOrderQuantity) {
                itemQuantity++;
                totalItemQuantity++;
                availableQuantity--;

                session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
            } else {
                request.setAttribute(ORDER_ERROR,
                        MessageManager.getInstance(locale).getProperty(ORDER_ERROR_MESSAGE));
            }
            page = PATH_TO_CART_PAGE;
        }

        if (removeButton != null && itemQuantity != 0) {
            itemQuantity = Integer.parseInt(receivedQuantity);
            itemQuantity--;
            totalItemQuantity--;
            availableQuantity++;
            session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
            page = PATH_TO_CART_PAGE;
        }

        Iterator iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            LineItem item = (LineItem) iterator.next();
            if (item.getProduct().getCode().equals(productCode)) {
                if (itemQuantity > 0) {
                    item.setQuantity(itemQuantity);
                } else {
                    iterator.remove();
                }
            }
        }

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findProductbyCode(productCode, language);
        session.setAttribute(Constant.PRODUCT, product);

        if (selectButton != null) {
            if (product != null && totalItemQuantity < maximumOrderQuantity) {
                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                lineItem.setQuantity(itemQuantity);
                cart.addItem(lineItem);
                totalItemQuantity++;
                availableQuantity--;

                session.setAttribute(Constant.CART, cart);
                session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);

                if (totalItemQuantity < maximumOrderQuantity) {

                    page = PATH_TO_SELECTING_ORDER_PAGE;
                } else {
                    page = PATH_TO_CART_PAGE;
                }
            } else {
                request.setAttribute(ORDER_ERROR,
                        MessageManager.getInstance(locale).getProperty(ORDER_ERROR_MESSAGE));
                page = PATH_TO_CART_PAGE;
            }
        }
        return page;
    }
}
