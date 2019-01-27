package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Cart;
import kz.epam.entities.LineItem;
import kz.epam.entities.Product;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddToCart implements Command {
//    static int itemQuantity = 1;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int itemQuantity = 1;

        String productCode = request.getParameter("productCode");
//        String addButton = request.getParameter("addButton");
//        String removeButton = request.getParameter("removeButton");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null)
        {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        product = productDAO.findProductbyCode(productCode);
        session.setAttribute("product", product);

        if (product != null) {
            if (cart.getItems().size() != 0) {
                boolean newProduct = false;
                for (LineItem item : cart.getItems()) {
                    if (item.getProduct().getCode().equals(productCode)) {
                        int quantity = item.getQuantity();
                        quantity++;
                        item.setQuantity(quantity);
                    }
                    else {
                        newProduct = true;

                    }
                }
                if (newProduct) {
                    LineItem item = new LineItem();
                    item.setProduct(product);
                    item.setQuantity(itemQuantity);
                    cart.addItem(item);
                }
            }
            else {
                LineItem item = new LineItem();
                item.setProduct(product);
                item.setQuantity(itemQuantity);
                cart.addItem(item);
            }
        }

        session.setAttribute("cart", cart);

        page = "/jsp/user/review_cart.jsp";

        return page;
    }
}
