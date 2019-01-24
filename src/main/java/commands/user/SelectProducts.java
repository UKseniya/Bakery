package commands.user;

import commands.Command;
import dao.ProductDAO;
import entities.Product;
import entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SelectProducts implements Command {
    private List<Product> availableProducts;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ProductDAO productDAO = new ProductDAO();
        availableProducts = productDAO.findAll();

//        HttpSession session = request.getSession(true);
        session.setAttribute("availableProducts", availableProducts);

        page = "/jsp/user/make_order.jsp";

        return page;
    }
}
