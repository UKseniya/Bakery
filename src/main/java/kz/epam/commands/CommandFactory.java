package kz.epam.commands;

import kz.epam.commands.admin.ShowTasks;
import kz.epam.commands.user.*;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static CommandFactory instance = null;
    enum CommandEnum {

        LOGIN, LOGOUT, REGISTRATION, REGISTRATION_FORM, USER_PAGE, SELECT_PRODUCTS,
        ADD_TO_CART, REVIEW_CART, UPDATE_CART, CHECKOUT, CONFIRM_ORDER, REVIEW_ORDERS,
        SHOW_ALL_ORDERS, SHOW_TASKS;
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public static Command getCommand(HttpServletRequest request) {
        // извлечение команды из запроса

        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            // если команда не задана в текущем запросе
            return new NoCommand();
        }
        // получение объекта, соответствующего команде
        CommandEnum current = CommandEnum.valueOf(action.toUpperCase());
        switch (current) {
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
            case REVIEW_ORDERS:
                return new ShowPendingOrders();
            case SHOW_ALL_ORDERS:
                return new ShowAllOrders();
            case SHOW_TASKS:
                return new ShowTasks();
            default:
                throw new EnumConstantNotPresentException(
                        current.getDeclaringClass(), current.name());
        }
    }
}
