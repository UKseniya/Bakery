package commands;

import commands.user.SelectProducts;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static CommandFactory instance = null;
    enum CommandEnum {

        LOGIN, LOGOUT, REGISTRATION, REGISTRATION_FORM, USER_PAGE, SELECT_PRODUCTS;
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
//            case CONFIRM_ORDER:
//                return new ConfirmOrder();
//            case ORDER:
//                return new OrderCommand();
//            case SHOW_USER_ORDERS:
//                return new ShowUserOrders();
//            case SHOW_DRINKS:
//                return new ShowDrinks();
//            case SHOW_INGREDIENTS:
//                return new ShowIngredients();
//            case REFILL_DRINKS:
//                return new RefillDrinks();
//            case REFILL_INGREDIENTS:
//                return new RefillIngredients();
            default:
                throw new EnumConstantNotPresentException(
                        current.getDeclaringClass(), current.name());
        }
    }
}
