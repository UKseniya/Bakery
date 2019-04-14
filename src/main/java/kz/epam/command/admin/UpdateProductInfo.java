package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UpdateProductInfo implements Command {

    private static final String PATH_TO_CONFIRMATION_PAGE = ConfigManager.getInstance().getProperty("path.page.product.info.confirmation");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0, 2);

        String code = request.getParameter(Constant.PRODUCT_CODE);
        String price = request.getParameter(Constant.PRODUCT_PRICE);
        String ruName = request.getParameter(Constant.RU_NAME);
        String enName = request.getParameter(Constant.EN_NAME);
        String ruDescription = request.getParameter(Constant.RU_PRODUCT_DESCRIPTION);
        String enDescription = request.getParameter(Constant.EN_PRODUCT_DESCRIPTION);

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findProductbyCode(code, locale);
        updateProductPrice(product, price);
        updateProductInfo(product, ruName, ruDescription, Constant.RU_LOCALE);
        updateProductInfo(product, enName, enDescription, Constant.EN_LOCALE);

        page = PATH_TO_CONFIRMATION_PAGE;

        return page;
    }

    private static void updateProductPrice (Product product, String price) {
        ProductDAO productDAO = new ProductDAO();
        product.setPrice(Double.parseDouble(price));
        productDAO.updatePrice(product);
    }

    private static void updateProductInfo (Product product, String name, String description, String locale) {
        ProductDAO productDAO = new ProductDAO();
        product.setName(name);
        product.setDescription(description);
        productDAO.update(product, locale);
    }
}
