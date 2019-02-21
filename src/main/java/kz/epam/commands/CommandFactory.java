package kz.epam.commands;

import kz.epam.commands.admin.*;
import kz.epam.commands.menu.ShowAvailableProducts;
import kz.epam.commands.user.*;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final String COMMAND = "command";
    private static CommandFactory instance = null;
    private static String action;
    enum CommandEnum {

        NO_COMMAND, LANGUAGE, LOGIN_FORM, LOGIN, LOGOUT, REGISTRATION, REGISTRATION_FORM, USER_PAGE, SELECT_PRODUCTS,
        ADD_TO_CART, REVIEW_CART, UPDATE_CART, CHECKOUT, CONFIRM_ORDER, REVIEW_ORDERS,
        SHOW_ALL_ORDERS, SHOW_TASKS, SHOW_ALL_REQUESTS, SHOW_AVAILABLE_PRODUCTS,
        UPDATE_PRODUCT_LIST, SHOW_PRODUCT_INFO, UPDATE_PRODUCT_INFO, ADD_NEW_PRODUCT,
        UPLOAD_PICTURE, SHOW_INCOME, SHOW_ANNUAL_INCOMES, SHOW_ALL_INCOMES;
    }

    public static CommandFactory getInstance() {
        return instance;
    }

//    public static String setCommand(String command) {
//        action = command;
//        return action;
//    }

    public static Command getCommand(HttpServletRequest request) {

        // Get the command from the request
        String command = request.getParameter(COMMAND);
        if (command == null || command.isEmpty()) {
            // if the command is not specified in the current request
            command = action;
        } else if (command != action){
            action = command;
        }
        // Receive object that corresponds to the request
        CommandEnum current = CommandEnum.valueOf(action.toUpperCase());
        switch (current) {
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
            case SELECT_PRODUCTS:
                return new SelectProducts();
            case ADD_TO_CART:
                return new AddToCart();
            case REVIEW_CART:
                return new ReviewCart();
            case UPDATE_CART:
                return new UpdateCart();
            case CHECKOUT:
                return new Checkout();
            case CONFIRM_ORDER:
                return new ConfirmOrder();
//            case REVIEW_ORDERS:
//                return new ShowPendingOrders();
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
            case UPLOAD_PICTURE:
                return new UploadPicture();
            case SHOW_INCOME:
                return new ShowIncome();
            case SHOW_ANNUAL_INCOMES:
                return new ShowAnnualIncomes();
            case SHOW_ALL_INCOMES:
                return new ShowAllIncomes();
            default:
                throw new EnumConstantNotPresentException(
                        current.getDeclaringClass(), current.name());
        }
    }
}
