package kz.epam.command;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.UserDAO;
import kz.epam.entity.User;
import kz.epam.message.MessageManager;
import kz.epam.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class Login implements Command {

    private static final int SALT_LENGTH = 30;
    private static final int SUBSTRING = 0;
    private static final String LOGIN_ERROR = "loginErrorMessage";
    private static final String ERROR_MESSAGE = "error.login";
    private static final String PATH_TO_LOGIN_PAGE = ConfigManager.getInstance().getProperty("path.command.login");
    private static final String PATH_TO_ADMIN_PAGE = ConfigManager.getInstance().getProperty("path.command.admin.page");
    private static final String PATH_TO_USER_SELECT_PAGE = ConfigManager.getInstance().getProperty("path.command.user.page");
    private static final String PATH_TO_USER_PAGE = ConfigManager.getInstance().getProperty("path.page.user.main");

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String password = null;

        String login = request.getParameter(Constant.LOGIN);
        String providedPassword = request.getParameter(Constant.PASSWORD);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);
        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language.substring(0,2));

        UserDAO userDAO = new UserDAO();

        if (user != null) {
            page = PATH_TO_USER_SELECT_PAGE;
        } else if (login != "") {

            // Retrieve secured password and salt from the password stored in DB.
            password = userDAO.findPasswordByLogin(login);

            boolean isUserRegistered = userDAO.isUserRegistered(login, password);

            if (isUserRegistered && verifyPassword(password, providedPassword)) {
                user = userDAO.findUserByLoginAndPassword(login, password);
                session.setAttribute(Constant.USER, user);

                if (user.getRole().equals(Constant.USER)) {
                    page = PATH_TO_USER_PAGE;
                } else if (user.getRole().equals(Constant.ADMIN)) {
                    page = PATH_TO_ADMIN_PAGE;
                }
                session.setAttribute(Constant.USER, user);
            } else {
                request.setAttribute(LOGIN_ERROR,
                        MessageManager.getInstance(locale).getProperty(ERROR_MESSAGE));
                page = PATH_TO_LOGIN_PAGE;
            }
        } else {
            request.setAttribute(LOGIN_ERROR, MessageManager.getInstance(locale).getProperty(ERROR_MESSAGE));
            page = PATH_TO_LOGIN_PAGE;
        }

        return page;
    }

    private boolean verifyPassword(String databasePassword, String providedPassword) {
        boolean verified = true;

        String securedPassword = databasePassword.substring(SUBSTRING, databasePassword.length() - SALT_LENGTH);
        String salt = databasePassword.length() > SALT_LENGTH ? databasePassword.substring(databasePassword.length() - SALT_LENGTH) : databasePassword;

        // Verify databasePassword provided by user
        verified = PasswordUtil.verifyUserPassword(providedPassword, securedPassword, salt);

        return verified;
    }
}
