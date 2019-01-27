package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

public class UpdateCart implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int quantity;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Cart cart = (Cart) session.getAttribute("cart");

        String productCode = request.getParameter("productCode");
        String receivedQuantity = request.getParameter("quantity");
        String addButton = request.getParameter("addButton");
        String removeButton = request.getParameter("removeButton");

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
//        ProductDAO productDAO = new ProductDAO();
//        Product product = new Product();
//        product = productDAO.findProductbyCode(productCode);
//        session.setAttribute("product", product);
//
//        if (product != null) {
//            LineItem item = new LineItem();
//            item.setProduct(product);
//            item.setQuantity(quantity);
//            if (quantity == 0) {
//                cart.removeItem(item);
//            }
//        }

        session.setAttribute("cart", cart);

        page = "/jsp/user/review_cart.jsp";

        return page;
    }
}
