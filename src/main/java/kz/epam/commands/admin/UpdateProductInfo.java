package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UpdateProductInfo implements Command {
    private static final String PRODUCT_CODE = "productCode";
    private static final String PRODUCT_PRICE = "price";
    private static final String RU_NAME = "russianName";
    private static final String EN_NAME = "englishName";
    private static final String RU_PRODUCT_DESCRIPTION = "russianDescription";
    private static final String EN_PRODUCT_DESCRIPTION = "englishDescription";
    private static final String RU_LOCALE = "ru";
    private static final String EN_LOCALE = "en";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/admin/successful_update.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();

        String code = request.getParameter(PRODUCT_CODE);
        String price = request.getParameter(PRODUCT_PRICE);
        String ruName = request.getParameter(RU_NAME);
        String enName = request.getParameter(EN_NAME);
        String ruDescription = request.getParameter(RU_PRODUCT_DESCRIPTION);
        String enDescription = request.getParameter(EN_PRODUCT_DESCRIPTION);

        ProductDAO productDAO = new ProductDAO();

        Product product = new Product();
        product = productDAO.findProductbyCode(code, locale);
        product.setPrice(Double.parseDouble(price));
        productDAO.updatePrice(product);
        product.setName(ruName);
        product.setDescription(ruDescription);
        productDAO.update(product, RU_LOCALE);
        product.setName(enName);
        product.setDescription(enDescription);
        productDAO.update(product, EN_LOCALE);

        page = PATH_TO_CONFIRMATION_PAGE;

        return page;
    }
}
