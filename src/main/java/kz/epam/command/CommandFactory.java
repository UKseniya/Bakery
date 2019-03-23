package kz.epam.command;

import kz.epam.command.admin.*;
import kz.epam.command.menu.ShowAvailableProducts;
import kz.epam.command.user.*;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final String COMMAND = "command";
    private static CommandFactory instance = null;
    private static String action;

    enum CommandEnum {

        NO_COMMAND, LOGIN_FORM, LOGIN, LOGOUT, REGISTRATION, REGISTRATION_FORM, USER_PAGE,
        UPDATE_USER_DETAILS, UPDATE_USER_PASSWORD, SELECT_DATE, SELECT_PRODUCTS, ADD_TO_CART, REVIEW_CART,
        CHECKOUT, CONFIRM_ORDER, ADMIN_PAGE, SHOW_ALL_ORDERS, SHOW_TASKS,
        SHOW_ALL_REQUESTS, SHOW_AVAILABLE_PRODUCTS, UPDATE_PRODUCT_LIST, SHOW_PRODUCT_INFO,
        UPDATE_PRODUCT_INFO, ADD_NEW_PRODUCT, SHOW_INCOME, SHOW_ANNUAL_INCOMES,
        SHOW_ALL_INCOMES, CANCEL_ORDER;
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public static Command getCommand(HttpServletRequest request) {

        // Get the command from the request
        String command = request.getParameter(COMMAND);

        if (!(command == null || command.isEmpty()) && command != action) {
            action = command;
        }

        if ((command == null || command.isEmpty()) && action.equalsIgnoreCase(CommandEnum.LOGIN.name())) {
            action = String.valueOf(CommandEnum.USER_PAGE).toLowerCase();
        }
        // Receive object that corresponds to the request
        CommandEnum current = CommandEnum.valueOf(action.toUpperCase());
        switch (current) {
            case NO_COMMAND:
                return new NoCommand();
            case LOGIN_FORM:
                return new GetLoginForm();
            case LOGIN:
                return new Login();
            case LOGOUT:
                return new Logout();
            case REGISTRATION:
                return new Registration();
            case REGISTRATION_FORM:
                return new RegistrationForm();
            case USER_PAGE:
                return new UserPage();
            case UPDATE_USER_DETAILS:
                return new UpdateUserDetails();
            case UPDATE_USER_PASSWORD:
                return new UpdateUserPassword();
            case SELECT_DATE:
                return new SelectDate();
            case SELECT_PRODUCTS:
                return new SelectProducts();
            case ADD_TO_CART:
                return new AddToCart();
            case REVIEW_CART:
                return new ReviewCart();
            case CHECKOUT:
                return new Checkout();
            case CONFIRM_ORDER:
                return new ConfirmOrder();
            case ADMIN_PAGE:
                return new AdminPage();
            case SHOW_ALL_ORDERS:
                return new ShowAllOrders();
            case SHOW_TASKS:
                return new ShowOpenRequests();
            case SHOW_ALL_REQUESTS:
                return new ShowAllRequests();
            case SHOW_AVAILABLE_PRODUCTS:
                return new ShowAvailableProducts();
            case UPDATE_PRODUCT_LIST:
                return new UpdateProductList();
            case SHOW_PRODUCT_INFO:
                return new ShowProductInfo();
            case UPDATE_PRODUCT_INFO:
                return new UpdateProductInfo();
            case ADD_NEW_PRODUCT:
                return new AddNewProduct();
            case SHOW_INCOME:
                return new ShowIncome();
            case SHOW_ANNUAL_INCOMES:
                return new ShowAnnualIncomes();
            case SHOW_ALL_INCOMES:
                return new ShowAllIncomes();
            case CANCEL_ORDER:
                return new CancelOrder();
            default:
                throw new EnumConstantNotPresentException(
                        current.getDeclaringClass(), current.name());
        }
    }
}
