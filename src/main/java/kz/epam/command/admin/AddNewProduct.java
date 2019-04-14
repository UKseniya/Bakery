package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Product;

import javax.servlet.http.HttpServletRequest;

public class AddNewProduct implements Command {

    private static final String PATH_TO_FORM = ConfigManager.getInstance().getProperty("path.page.add.product");
    private static final String PATH_TO_PICTURE_UPLOAD = ConfigManager.getInstance().getProperty("path.page.upload.picture");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String addButton = request.getParameter(Constant.ADD_BUTTON);
        String code = request.getParameter(Constant.PRODUCT_CODE);
        String price = request.getParameter(Constant.PRODUCT_PRICE);
        String ruName = request.getParameter(Constant.RU_NAME);
        String enName = request.getParameter(Constant.EN_NAME);
        String ruDescription = request.getParameter(Constant.RU_PRODUCT_DESCRIPTION);
        String enDescription = request.getParameter(Constant.EN_PRODUCT_DESCRIPTION);

        if (addButton != null) {
            addProductToDatabase(code, price);
            addProductDescriptionToDatabase(ruName, ruDescription, Constant.RU_LOCALE);
            addProductDescriptionToDatabase(enName, enDescription, Constant.EN_LOCALE);

            page = PATH_TO_PICTURE_UPLOAD;
        } else {
            page = PATH_TO_FORM;
        }
        return page;
    }

    private static void addProductToDatabase(String code, String price){
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        product.setCode(code);
        product.setPrice(Double.parseDouble(price));
        productDAO.create(product);
    }

    private static void addProductDescriptionToDatabase(String name, String description, String locale) {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        productDAO.addNewProductDescription(product, locale);
    }
}
