package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

public class UpdateCart implements Command {
    private static final String QUANTITY = "quantity";
    private static final String ADD_BUTTON = "addButton";
    private static final String REMOVE_BUTTON = "removeButton";
    private static final String PATH_TO_VIEW_CART = "/jsp/user/review_cart.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int quantity;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        Cart cart = (Cart) session.getAttribute(Constants.CART);

        String productCode = request.getParameter(Constants.PRODUCT_CODE);
        String receivedQuantity = request.getParameter(QUANTITY);
        String addButton = request.getParameter(ADD_BUTTON);
        String removeButton = request.getParameter(REMOVE_BUTTON);

        quantity = Integer.parseInt(receivedQuantity);

        if (addButton != null) {
            quantity++;
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
