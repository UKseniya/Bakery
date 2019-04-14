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
import java.util.List;
import java.util.Locale;

public class AddToCart implements Command {

    private static final String QUANTITY = "quantity";
    private static final String ZERO = "0";
    private static final String ORDER_ERROR = "orderErrorMessage";
    private static final String ORDER_ERROR_MESSAGE = "error.order.full";
    private static final String PATH_TO_SELECTING_ORDER_PAGE = ConfigManager.getInstance().getProperty("path.page.ordering");
    private static final String PATH_TO_CART_PAGE = ConfigManager.getInstance().getProperty("path.page.cart.review");
    private static final String AVAILABLE_QUANTITY = "availableQuantity";

    private static int totalItemsInOrder = 0;
    private static int maximumOrderQuantity = 0;
    private static int availableQuantity;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int itemQuantity = 1;

        String productCode = request.getParameter(Constant.PRODUCT_CODE);
        String currentQuantity = request.getParameter(QUANTITY);
        String addProductToCartButton = request.getParameter(Constant.SELECT_BUTTON);
        String increaseItemNumberButton = request.getParameter(Constant.ADD_BUTTON);
        String decreaseItemNumberButton = request.getParameter(Constant.REMOVE_BUTTON);
        String quantityToOrder = request.getParameter(AVAILABLE_QUANTITY);

        HttpSession session = request.getSession();

        String language = session.getAttribute(Constant.LOCALE).toString();
        Locale locale = new Locale(language.substring(0, 2));

        Cart cart = (Cart) session.getAttribute(Constant.CART);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(Constant.CART, cart);
        }

        if (increaseItemNumberButton != null) {
            boolean increasedQuantity = increaseItemNumber(session, quantityToOrder, currentQuantity, productCode, cart);
            if (increasedQuantity) {
                session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
            } else {
                request.setAttribute(ORDER_ERROR,
                        MessageManager.getInstance(locale).getProperty(ORDER_ERROR_MESSAGE));
            }
            page = PATH_TO_CART_PAGE;
        }

        if (decreaseItemNumberButton != null && !currentQuantity.equals(ZERO)) {
            itemQuantity = getDescreasedItemQuantity(currentQuantity, quantityToOrder);
            updateItemQuantity(cart.getItems(), productCode, itemQuantity);
            session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
            page = PATH_TO_CART_PAGE;
        }

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findProductbyCode(productCode, language.substring(0, 2));
        session.setAttribute(Constant.PRODUCT, product);

        if (addProductToCartButton != null) {
            boolean productAdded = addProductToCart(session, quantityToOrder, product, cart, itemQuantity);
            if (productAdded) {
                session.setAttribute(Constant.CART, cart);
                session.setAttribute(AVAILABLE_QUANTITY, availableQuantity);
                page = determinePagePath(session, quantityToOrder);
            } else {
                request.setAttribute(ORDER_ERROR,
                        MessageManager.getInstance(locale).getProperty(ORDER_ERROR_MESSAGE));
                page = PATH_TO_CART_PAGE;
            }
        }
        return page;
    }

    private static boolean increaseItemNumber (HttpSession session, String quantityToOrder, String currentQuantity, String productCode, Cart cart) {
        if (getTotalItemsInOrder(session) < getMaximumOrderQuantity(quantityToOrder)) {
            int itemQuantity = getIncreasedItemQuantity(currentQuantity, quantityToOrder);
            updateItemQuantity(cart.getItems(), productCode, itemQuantity);
            return true;
        }
        return false;
    }

    private static void updateItemQuantity(List<LineItem> items, String productCode, int currentQuantity) {
        Iterator<LineItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            LineItem item = iterator.next();
            if (item.getProduct().getCode().equals(productCode)) {
                if (currentQuantity > 0) {
                    item.setQuantity(currentQuantity);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    private static boolean addProductToCart(HttpSession session, String quantityToOrder, Product product, Cart cart, int itemQuantity) {
        if (!isMaximumOrdered(session, quantityToOrder)) {
            cart.addItem(setItem(product, itemQuantity));
            updateOrderItemNumber(quantityToOrder);
            return true;
        }
        return false;
    }

    private static boolean isMaximumOrdered(HttpSession session, String quantityToOrder) {
        if (getTotalItemsInOrder(session) == getMaximumOrderQuantity(quantityToOrder)) {
            return true;
        }
        return false;
    }
    private static String determinePagePath(HttpSession session, String quantityToOrder) {
        String page;
        if (isMaximumOrdered(session, quantityToOrder)) {
            page = PATH_TO_CART_PAGE;
        } else {
            page = PATH_TO_SELECTING_ORDER_PAGE;
        }
        return page;
    }

    private static int getMaximumOrderQuantity(String quantity) {
        if (maximumOrderQuantity == 0) {
            maximumOrderQuantity = Integer.parseInt(quantity);
        }
        return maximumOrderQuantity;
    }

    private static int getAvailableQuantity(String quantity) {
        if (quantity != null) {
            availableQuantity = Integer.parseInt(quantity);
        }
        return availableQuantity;
    }

    private static int getTotalItemsInOrder(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(Constant.CART);
        if (cart == null) {
            totalItemsInOrder = 0;
        }
        return totalItemsInOrder;
    }

    private static int getIncreasedItemQuantity(String currentQuantity, String quantityToOrder) {
        int itemQuantity = Integer.parseInt(currentQuantity);
        itemQuantity++;
        totalItemsInOrder++;
        availableQuantity = getAvailableQuantity(quantityToOrder);
        availableQuantity--;
        return itemQuantity;
    }

    private static int getDescreasedItemQuantity(String currentQuantity, String quantityToOrder) {
        int itemQuantity = Integer.parseInt(currentQuantity);
        itemQuantity--;
        totalItemsInOrder--;
        availableQuantity = getAvailableQuantity(quantityToOrder);
        availableQuantity++;
        return itemQuantity;
    }

    private static LineItem setItem(Product product, int quantity) {
        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(quantity);
        return lineItem;
    }

    private static void updateOrderItemNumber(String quantity) {
        totalItemsInOrder++;
        availableQuantity = getAvailableQuantity(quantity);
        availableQuantity--;
    }
}
