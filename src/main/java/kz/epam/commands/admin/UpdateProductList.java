package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UpdateProductList implements Command {
    private static final String REMOVE_BUTTON = "removeButton";
    private static final String ADD_BUTTON = "addButton";
    private static final String PRODUCT_CODE = "productCode";
    private static final String DISABLED_PRODUCTS = "cancelledProducts";
    private static final String AVAILABLE_STATUS = "available";
    private static final String DISABLED_STATUS = "n/a";
    private static final String PATH = "/jsp/admin/update_product_list.jsp";

    private List<Product> availableProducts;
    private List<Product> cancelledProducts;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();

        String removeButton = request.getParameter(REMOVE_BUTTON);
        String addButton = request.getParameter(ADD_BUTTON);
        String code = request.getParameter(PRODUCT_CODE);

        ProductDAO productDAO = new ProductDAO();

        if (removeButton != null) {
            Product product = new Product();
            product = productDAO.findProductbyCode(code, locale);
            productDAO.updateProductStatus(product, DISABLED_STATUS);
        }

        if (addButton != null) {
            Product product = new Product();
            product = productDAO.findProductbyCode(code, locale);
            productDAO.updateProductStatus(product, AVAILABLE_STATUS);
        }

        availableProducts = productDAO.findAllAvailableProducts(locale);

        session.setAttribute(Constants.AVAILABLE_PRODUCTS, availableProducts);

        cancelledProducts = productDAO.findAllCancelledProducts(locale);

        session.setAttribute(DISABLED_PRODUCTS, cancelledProducts);

        page = PATH;

        return page;
    }
}
