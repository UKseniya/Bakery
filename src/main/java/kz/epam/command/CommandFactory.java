package kz.epam.command;

import kz.epam.command.admin.*;
import kz.epam.command.menu.ShowAvailableProducts;
import kz.epam.command.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.EnumMap;

public class CommandFactory {

    private static final String COMMAND = "command";
    private static CommandFactory instance = new CommandFactory();
    private static String action;
    private EnumMap<CommandEnum, Command> commands = new EnumMap<>(CommandEnum.class);

    private CommandFactory() {
        commands.put(CommandEnum.NO_COMMAND, new NoCommand());
        commands.put(CommandEnum.LOGIN_FORM, new GetLoginForm());
        commands.put(CommandEnum.LOGIN, new Login());
        commands.put(CommandEnum.LOGOUT, new Logout());
        commands.put(CommandEnum.REGISTRATION, new Registration());
        commands.put(CommandEnum.REGISTRATION_FORM, new RegistrationForm());
        commands.put(CommandEnum.USER_PAGE, new UserPage());
        commands.put(CommandEnum.UPDATE_USER_DETAILS, new UpdateUserDetails());
        commands.put(CommandEnum.UPDATE_USER_PASSWORD, new UpdateUserPassword());
        commands.put(CommandEnum.SELECT_DATE, new SelectDate());
        commands.put(CommandEnum.SELECT_PRODUCTS, new SelectProducts());
        commands.put(CommandEnum.ADD_TO_CART, new AddToCart());
        commands.put(CommandEnum.REVIEW_CART, new ReviewCart());
        commands.put(CommandEnum.CHECKOUT, new Checkout());
        commands.put(CommandEnum.CONFIRM_ORDER, new ConfirmOrder());
        commands.put(CommandEnum.SHOW_ALL_ORDERS, new ShowAllOrders());
        commands.put(CommandEnum.ADMIN_PAGE, new AdminPage());
        commands.put(CommandEnum.SHOW_TASKS, new ShowOpenRequests());
        commands.put(CommandEnum.SHOW_ALL_REQUESTS, new ShowAllRequests());
        commands.put(CommandEnum.SHOW_AVAILABLE_PRODUCTS, new ShowAvailableProducts());
        commands.put(CommandEnum.UPDATE_PRODUCT_LIST, new UpdateProductList());
        commands.put(CommandEnum.UPDATE_PRODUCT_INFO, new UpdateProductInfo());
        commands.put(CommandEnum.SHOW_PRODUCT_INFO, new ShowProductInfo());
        commands.put(CommandEnum.ADD_NEW_PRODUCT, new AddNewProduct());
        commands.put(CommandEnum.SHOW_INCOME, new ShowIncome());
        commands.put(CommandEnum.SHOW_ANNUAL_INCOMES, new ShowAnnualIncomes());
        commands.put(CommandEnum.SHOW_ALL_INCOMES, new ShowAllIncomes());
        commands.put(CommandEnum.CANCEL_ORDER, new CancelOrder());

    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {

        String commandName = getAction(request);
        return commands.get(CommandEnum.valueOf(commandName));
    }

    private static String getAction(HttpServletRequest request) {
        String receivedCommand = request.getParameter(COMMAND);

        if (!(receivedCommand == null || receivedCommand.isEmpty()) && receivedCommand != action) {
            action = receivedCommand.toUpperCase();
        }

        if ((receivedCommand == null || receivedCommand.isEmpty()) && action.equalsIgnoreCase(CommandEnum.LOGIN.name())) {
            action = String.valueOf(CommandEnum.USER_PAGE);
        }
        return action;
    }
}
