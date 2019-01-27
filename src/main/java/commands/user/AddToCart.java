package commands.user;

import commands.Command;

import dao.ProductDAO;
import entities.Cart;
import entities.LineItem;
import entities.Product;
import entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddToCart implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int quantity = 1;

        String productCode = request.getParameter("productCode");
//        String receivedQuantity = request.getParameter("quantity");
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
            LineItem item = new LineItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.addItem(item);
        }

        session.setAttribute("cart", cart);

        page = "/jsp/user/review_cart.jsp";

        return page;
    }
}
