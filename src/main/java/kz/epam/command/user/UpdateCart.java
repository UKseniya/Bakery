package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Locale;

public class UpdateCart implements Command {

    private static final int MAXIMUM_ORDER_QUANTITY = 5;
    private static final String QUANTITY = "quantity";
    private static final String ADD_BUTTON = "addButton";
    private static final String REMOVE_BUTTON = "removeButton";
    private static final String ORDER_ERROR = "orderErrorMessage";
    private static final String ORDER_ERROR_MESSAGE = "error.order.full";
    private static final String PATH_TO_VIEW_CART = "/jsp/user/review_cart.jsp";


    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int totalItemQuantity = 0;
        int quantity;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constants.LOCALE).toString();
        User user = (User) session.getAttribute(Constants.USER);

        Locale locale = new Locale(language);

        Cart cart = (Cart) session.getAttribute(Constants.CART);

        String productCode = request.getParameter(Constants.PRODUCT_CODE);
        String receivedQuantity = request.getParameter(QUANTITY);
        String addButton = request.getParameter(ADD_BUTTON);
        String removeButton = request.getParameter(REMOVE_BUTTON);

        for (LineItem item : cart.getItems()) {
            totalItemQuantity = item.getQuantity() + totalItemQuantity;
        }

        quantity = Integer.parseInt(receivedQuantity);

        if (addButton != null) {
            if (totalItemQuantity < MAXIMUM_ORDER_QUANTITY){
                quantity++;
            }
            else {
                request.setAttribute(ORDER_ERROR,
                        MessageManager.getInstance(locale).getProperty(ORDER_ERROR_MESSAGE));
            }
        }

        if (removeButton != null && quantity != 0) {
            quantity--;
        }

        Iterator iterator = cart.getItems().iterator();

        while (iterator.hasNext()) {
            LineItem item = (LineItem) iterator.next();
            if (item.getProduct().getCode().equals(productCode)) {
                if (quantity > 0 ) {
                    item.setQuantity(quantity);
                }
                else {
                    iterator.remove();
                }
            }
        }

        session.setAttribute(Constants.CART, cart);

        page = PATH_TO_VIEW_CART;

        return page;
    }
}
