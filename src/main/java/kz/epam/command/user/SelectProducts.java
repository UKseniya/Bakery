package kz.epam.command.user;

import kz.epam.command.Command;
import kz.epam.constant.Constant;
import kz.epam.dao.ProductDAO;
import kz.epam.entity.Product;
import kz.epam.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SelectProducts implements Command {

    private static final String PATH_TO_SELECTING_ORDER_PAGE = "/jsp/user/make_order.jsp";

    private List<Product> availableProducts;

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString();

        ProductDAO productDAO = new ProductDAO();
        availableProducts = productDAO.findAllAvailableProducts(locale);

        session.setAttribute(Constant.AVAILABLE_PRODUCTS, availableProducts);

        page = PATH_TO_SELECTING_ORDER_PAGE;

        return page;
    }
}
