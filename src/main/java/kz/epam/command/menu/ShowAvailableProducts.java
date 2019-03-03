package kz.epam.command.menu;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Product;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAvailableProducts implements Command {

    private static final String PATH_TO_PRODUCT_LIST = "/jsp/menu/products.jsp";
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

        page = PATH_TO_PRODUCT_LIST;

        return page;
    }
}
