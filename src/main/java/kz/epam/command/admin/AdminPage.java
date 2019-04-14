package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.LineItemDAO;
import kz.epam.dao.UserDAO;
import kz.epam.entity.LineItem;
import kz.epam.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminPage implements Command {

    private static final String TOTAL_NUMBER_OF_USERS = "numberOfUsers";
    private static final String CURRENT_MONTH_TOP_PRODUCTS = "currentMonthTopProducts";
    private static final String PREVIOUS_MONTH_TOP_PRODUCTS = "previousMonthTopProducts";
    private static final String PATH_TO_ADMIN_PAGE = ConfigManager.getInstance().getProperty("path.page.admin.main");

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constant.LOCALE).toString().substring(0, 2);

        int numberOfUsers = getTotalNumberOfUsers();
        session.setAttribute(TOTAL_NUMBER_OF_USERS, numberOfUsers);

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int year = today.getYear();

        List<LineItem> currentMonthTopProducts = getTopProductOfMonth(getAllProductsOrderedForMonth(currentMonth, year, locale));
        session.setAttribute(CURRENT_MONTH_TOP_PRODUCTS, currentMonthTopProducts);

        int previousMonth;
        if (currentMonth == Constant.FIRST_MONTH) {
            previousMonth = Constant.LAST_MONTH;
            year = year - Constant.DECREMENT;
        } else {
            previousMonth = currentMonth - Constant.DECREMENT;
        }

        List<LineItem> previousMonthTopProducts = getTopProductOfMonth(getAllProductsOrderedForMonth(previousMonth, year, locale));
        session.setAttribute(PREVIOUS_MONTH_TOP_PRODUCTS, previousMonthTopProducts);

        return PATH_TO_ADMIN_PAGE;
    }

    private static int getTotalNumberOfUsers() {
        UserDAO userDAO = new UserDAO();
        List<User> registeredUsers = userDAO.findAllUsersByRole(Constant.USER);
        return registeredUsers.size();
    }

    private static List<LineItem> getAllProductsOrderedForMonth(int month, int year, String locale) {
        LineItemDAO lineItemDAO = new LineItemDAO();
        return lineItemDAO.findTopProducts(month, year, locale);
    }

    private static List<LineItem> getTopProductOfMonth (List<LineItem> items) {
        List<LineItem> topProducts = new ArrayList<>();
        int maxQuantity = 0;
        for (LineItem lineItem : items) {
            if (lineItem.getQuantity() > maxQuantity) {
                maxQuantity = lineItem.getQuantity();
            }
        }

        for (LineItem lineItem : items) {
            if (lineItem.getQuantity() == maxQuantity) {
                topProducts.add(lineItem);
            }
        }
        return topProducts;
    }
}
