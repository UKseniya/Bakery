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
        String locale = session.getAttribute(Constant.LOCALE).toString();

        String code = request.getParameter(Constant.PRODUCT_CODE);
        String price = request.getParameter(Constant.PRODUCT_PRICE);
        String ruName = request.getParameter(Constant.RU_NAME);
        String enName = request.getParameter(Constant.EN_NAME);
        String ruDescription = request.getParameter(Constant.RU_PRODUCT_DESCRIPTION);
        String enDescription = request.getParameter(Constant.EN_PRODUCT_DESCRIPTION);

        ProductDAO productDAO = new ProductDAO();

        Product product = productDAO.findProductbyCode(code, locale);
        product.setPrice(Double.parseDouble(price));
        productDAO.updatePrice(product);
        product.setName(ruName);
        product.setDescription(ruDescription);
        productDAO.update(product, Constant.RU_LOCALE);
        product.setName(enName);
        product.setDescription(enDescription);
        productDAO.update(product, Constant.EN_LOCALE);

        page = PATH_TO_CONFIRMATION_PAGE;

        return page;
    }
}
