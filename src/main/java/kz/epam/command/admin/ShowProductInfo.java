package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowProductInfo implements Command {

    private static final String PRODUCT_CODE = "productCode";
    private static final String PRODUCT_IN_RUSSIAN = "productRu";
    private static final String PRODUCT_IN_ENGLISH = "productEn";
    private static final String RU_LOCALE = "ru";
    private static final String EN_LOCALE = "en";
    private static final String PATH_TO_UPDATE_PRODUCT_INFO = "/jsp/admin/update_product_info.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String productCode = request.getParameter(PRODUCT_CODE);

        ProductDAO productDAO = new ProductDAO();

        Product productRu = new Product();
        productRu = productDAO.findProductbyCode(productCode, RU_LOCALE);
        Product productEn = new Product();
        productEn = productDAO.findProductbyCode(productCode, EN_LOCALE);

        session.setAttribute(PRODUCT_IN_RUSSIAN, productRu);
        session.setAttribute(PRODUCT_IN_ENGLISH, productEn);

        page = PATH_TO_UPDATE_PRODUCT_INFO;

        return page;
    }
}
