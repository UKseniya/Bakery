package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UpdateProductList implements Command {

    private static final String DISABLED_PRODUCTS = "cancelledProducts";
    private static final String AVAILABLE_STATUS = "available";
    private static final String DISABLED_STATUS = "n/a";
    private static final String PATH_TO_PRODUCT_UPDATE_PAGE = ConfigManager.getInstance().getProperty("path.page.update.product.list");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0,2);

        String removeButton = request.getParameter(Constant.REMOVE_BUTTON);
        String addButton = request.getParameter(Constant.ADD_BUTTON);
        String code = request.getParameter(Constant.PRODUCT_CODE);

        ProductDAO productDAO = new ProductDAO();

        if (removeButton != null) {
            Product product = productDAO.findProductbyCode(code, locale);
            productDAO.updateProductStatus(product, DISABLED_STATUS);
        }

        if (addButton != null) {
            Product product = productDAO.findProductbyCode(code, locale);
            productDAO.updateProductStatus(product, AVAILABLE_STATUS);
        }

        List<Product> availableProducts = productDAO.findAllAvailableProducts(locale);

        session.setAttribute(Constant.AVAILABLE_PRODUCTS, availableProducts);

        List<Product> cancelledProducts = productDAO.findAllCancelledProducts(locale);

        session.setAttribute(DISABLED_PRODUCTS, cancelledProducts);

        page = PATH_TO_PRODUCT_UPDATE_PAGE;

        return page;
    }
}
