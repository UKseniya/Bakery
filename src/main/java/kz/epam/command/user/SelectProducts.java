package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SelectProducts implements Command {

    private static final String PATH_TO_SELECTING_ORDER_PAGE = ConfigManager.getInstance().getProperty("path.page.ordering");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0,2);

        ProductDAO productDAO = new ProductDAO();
        List<Product> availableProducts = productDAO.findAllAvailableProducts(locale);

        session.setAttribute(Constant.AVAILABLE_PRODUCTS, availableProducts);

        page = PATH_TO_SELECTING_ORDER_PAGE;

        return page;
    }
}
