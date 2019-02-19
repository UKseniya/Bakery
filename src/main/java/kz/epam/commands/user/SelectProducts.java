package kz.epam.commands.user;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Product;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SelectProducts implements Command {
    private static final String PATH_TO_SELECTING_ORDER_PAGE = "/jsp/user/make_order.jsp";

    private List<Product> availableProducts;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();
        User user = (User) session.getAttribute(Constants.USER);

        ProductDAO productDAO = new ProductDAO();
        availableProducts = productDAO.findAllAvailableProducts(locale);

        session.setAttribute(Constants.AVAILABLE_PRODUCTS, availableProducts);

        page = PATH_TO_SELECTING_ORDER_PAGE;

        return page;
    }
}
