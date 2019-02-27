package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.ProductDAO;
import kz.epam.entities.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddNewProduct implements Command {

    private static final String PRODUCT_CODE = "productCode";
    private static final String PRODUCT_PRICE = "price";
    private static final String RU_NAME = "russianName";
    private static final String EN_NAME = "englishName";
    private static final String RU_PRODUCT_DESCRIPTION = "russianDescription";
    private static final String EN_PRODUCT_DESCRIPTION = "englishDescription";
    private static final String RU_LOCALE = "ru";
    private static final String EN_LOCALE = "en";
    private static final String ADD_BUTTON = "addButton";
    private static final String PATH_TO_FORM = "/jsp/admin/add_new_product.jsp";
    private static final String PATH_TO_PICTURE_UPLOAD = "/jsp/admin/upload_picture.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();

        String addButton = request.getParameter(ADD_BUTTON);
        String code = request.getParameter(PRODUCT_CODE);
        String price = request.getParameter(PRODUCT_PRICE);
        String ruName = request.getParameter(RU_NAME);
        String enName = request.getParameter(EN_NAME);
        String ruDescription = request.getParameter(RU_PRODUCT_DESCRIPTION);
        String enDescription = request.getParameter(EN_PRODUCT_DESCRIPTION);

        if (addButton != null) {
            ProductDAO productDAO = new ProductDAO();

            Product product = new Product();
            product.setCode(code);
            product.setPrice(Double.parseDouble(price));
            productDAO.create(product);
            product.setName(ruName);
            product.setDescription(ruDescription);
            productDAO.addNewProductDescription(product, RU_LOCALE);
            product.setName(enName);
            product.setDescription(enDescription);
            productDAO.addNewProductDescription(product, EN_LOCALE);

            page = PATH_TO_PICTURE_UPLOAD;
        } else {
            page = PATH_TO_FORM;
        }

        return page;
    }
}
