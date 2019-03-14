package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Cart;
import kz.epam.entity.LineItem;
import kz.epam.entity.Product;
import kz.epam.entity.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Locale;

public class AddToCart implements Command {

    private static final int MAXIMUM_ORDER_QUANTITY = 5;
    private static final String QUANTITY = "quantity";
    private static final String SELECT_BUTTON = "selectButton";
    private static final String ADD_BUTTON = "addButton";
    private static final String REMOVE_BUTTON = "removeButton";
    private static final String ORDER_ERROR = "orderErrorMessage";
    private static final String ORDER_ERROR_MESSAGE = "error.order.full";
    private static final String PATH_TO_SELECTING_ORDER_PAGE = "/jsp/user/make_order.jsp";
    private static final String PATH_TO_CART_PAGE = "/jsp/user/review_cart.jsp";

    private static int totalItemQuantity = 0;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int itemQuantity = 1;

        String productCode = request.getParameter(Constant.PRODUCT_CODE);
        String receivedQuantity = request.getParameter(QUANTITY);
        String selectButton = request.getParameter(SELECT_BUTTON);
        String addButton = request.getParameter(ADD_BUTTON);
        String removeButton = request.getParameter(REMOVE_BUTTON);

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constant.LOCALE).toString();
        User user = (User) session.getAttribute(Constant.USER);

        Locale locale = new Locale(language);

        Cart cart = (Cart) session.getAttribute(Constant.CART);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(Constant.CART, cart);
        }

        if (addButton != null) {
            itemQuantity = Integer.parseInt(receivedQuantity);

            if (totalItemQuantity < MAXIMUM_ORDER_QUANTITY) {
                itemQuantity++;
                totalItemQuantity++;
            } else {
                request.setAttribute(ORDER_ERROR,
                        MessageManager.getInstance(locale).getProperty(ORDER_ERROR_MESSAGE));
            }
            page = PATH_TO_CART_PAGE;
        }

        if (removeButton != null && itemQuantity != 0) {
            itemQuantity = Integer.parseInt(receivedQuantity);
            itemQuantity--;
//            for (LineItem item : cart.getItems()) {
//                totalItemQuantity = item.getQuantity() + totalItemQuantity;
//            }
            totalItemQuantity--;
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
            if (product != null && totalItemQuantity < MAXIMUM_ORDER_QUANTITY) {
                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                lineItem.setQuantity(itemQuantity);
                cart.addItem(lineItem);
                totalItemQuantity++;

                session.setAttribute(Constant.CART, cart);

                if (totalItemQuantity < MAXIMUM_ORDER_QUANTITY) {

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
