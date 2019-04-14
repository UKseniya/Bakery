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

    private static final int SUBSTRING = 0;
    private static final String LOGIN_ERROR = "loginErrorMessage";
    private static final String ERROR_MESSAGE = "error.login";
    private static final String PATH_TO_LOGIN_PAGE = ConfigManager.getInstance().getProperty("path.command.login");
    private static final String PATH_TO_ADMIN_PAGE = ConfigManager.getInstance().getProperty("path.command.admin.page");
    private static final String PATH_TO_USER_PAGE = ConfigManager.getInstance().getProperty("path.page.user.main");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String login = request.getParameter(Constant.LOGIN);
        String providedPassword = request.getParameter(Constant.PASSWORD);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);
        String language = session.getAttribute(Constant.LOCALE).toString();
        Locale locale = new Locale(language.substring(0, 2));

        UserDAO userDAO = new UserDAO();

        if (user != null) {
            page = determinePage(user);
        } else if (isLoginAndPasswordCorrect(login, providedPassword)) {
            String password = userDAO.findPasswordByLogin(login);
            user = userDAO.findUserByLoginAndPassword(login, password);
            page = determinePage(user);
            session.setAttribute(Constant.USER, user);
        } else {
            request.setAttribute(LOGIN_ERROR, MessageManager.getInstance(locale).getProperty(ERROR_MESSAGE));
            page = PATH_TO_LOGIN_PAGE;

        }
        return page;
    }

    private static boolean verifyPassword(String databasePassword, String providedPassword) {
        boolean verified;

        String securedPassword = databasePassword.substring(SUBSTRING, databasePassword.length() - Constant.SALT);
        String salt = databasePassword.length() > Constant.SALT ? databasePassword.substring(databasePassword.length() - Constant.SALT) : databasePassword;

        verified = PasswordUtil.verifyUserPassword(providedPassword, securedPassword, salt);

        return verified;
    }

    private static String determinePage(User user) {
        String page = null;
        if (user.getRole().equals(Constant.USER)) {
            page = PATH_TO_USER_PAGE;
        } else if (user.getRole().equals(Constant.ADMIN)) {
            page = PATH_TO_ADMIN_PAGE;
        }
        return page;
    }

    private static boolean isLoginAndPasswordCorrect(String login, String providedPassword) {
        UserDAO userDAO = new UserDAO();
        if (!login.isEmpty()) {
            String password = userDAO.findPasswordByLogin(login);
            if (verifyPassword(password, providedPassword)) {
                return true;
            }
        }
        return false;
    }
}
