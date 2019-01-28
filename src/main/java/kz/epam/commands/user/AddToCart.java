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

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        int itemQuantity = 1;

        String productCode = request.getParameter("productCode");

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
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(itemQuantity);
                cart.addItem(lineItem);
        }

        session.setAttribute("cart", cart);

        page = "/jsp/user/review_cart.jsp";

        return page;
    }
}
