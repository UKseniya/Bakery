package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.LineItemDAO;
import kz.epam.dao.UserDAO;
import kz.epam.entities.LineItem;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminPage implements Command {

    private static final String TOTAL_NUMBER_OF_USERS = "numberOfUsers";
    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;
    private static final int DECREMENT = 1;
    private List<User> registeredUsers = new ArrayList<>();
    private static final String CURRENT_MONTH_TOP_PRODUCTS = "currentMonthTopProducts";
    private static final String PREVIOUS_MONTH_TOP_PRODUCTS = "previousMonthTopProducts";
    private List<LineItem> currentMonthTopProducts = new ArrayList<>();
    private List<LineItem> previousMonthTopProducts = new ArrayList<>();
    private int maxQuantity = 0;
    private int year;
    private int currentMonth;
    private int previousMonth;

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();

        UserDAO userDAO = new UserDAO();
        registeredUsers = userDAO.findAllUsersByRole(Constants.USER);
        int numberOfUsers = registeredUsers.size();

        session.setAttribute(TOTAL_NUMBER_OF_USERS, numberOfUsers);

        LocalDate today = LocalDate.now();
        currentMonth = today.getMonthValue();
        year = today.getYear();

        LineItemDAO lineItemDAO = new LineItemDAO();
        List<LineItem> currentMonthProducts = lineItemDAO.findTopProducts(currentMonth, year, locale);

        for (LineItem lineItem : currentMonthProducts) {
            if (lineItem.getQuantity() > maxQuantity) {
                maxQuantity = lineItem.getQuantity();
            }
        }

        for (LineItem lineItem : currentMonthProducts) {
            if (lineItem.getQuantity() == maxQuantity) {
                currentMonthTopProducts.add(lineItem);
            }
        }

        session.setAttribute(CURRENT_MONTH_TOP_PRODUCTS, currentMonthTopProducts);

        if (currentMonth == FIRST_MONTH) {
            previousMonth = LAST_MONTH;
            year = year - DECREMENT;
        } else {
            previousMonth = currentMonth - DECREMENT;
        }

        List<LineItem> previousMonthProducts = lineItemDAO.findTopProducts(previousMonth, year, locale);

        for (LineItem lineItem : previousMonthProducts) {
            if (lineItem.getQuantity() >= maxQuantity) {
                maxQuantity = lineItem.getQuantity();
            }
        }

        for (LineItem lineItem : previousMonthProducts) {
            if (lineItem.getQuantity() == maxQuantity) {
                previousMonthTopProducts.add(lineItem);
            }
        }

        session.setAttribute(PREVIOUS_MONTH_TOP_PRODUCTS, previousMonthTopProducts);


        return Constants.PATH_TO_ADMIN_PAGE;
    }
}
