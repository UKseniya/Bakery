package kz.epam.command.menu;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Product;
import kz.epam.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAvailableProducts implements Command {

    private static final String PATH_TO_PRODUCT_LIST = "/jsp/menu/products.jsp";

    private List<Product> availableProducts;

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0,2);
        User user = (User) session.getAttribute(Constant.USER);

        ProductDAO productDAO = new ProductDAO();
        availableProducts = productDAO.findAllAvailableProducts(locale);

        session.setAttribute(Constant.AVAILABLE_PRODUCTS, availableProducts);

        page = PATH_TO_PRODUCT_LIST;

        return page;
    }
}
