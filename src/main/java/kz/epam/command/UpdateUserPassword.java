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

public class UpdateUserPassword implements Command {

    private static final int SUBSTRING = 0;
    private static final String CURRENT_PASSWORD = "currentPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String INCORRECT_PASSWORD_MESSAGE = "incorrectPasswordMessage";
    private static final String INCORRECT_PASSWORD = "error.incorrect.password";
    private static final String SAME_PASSWORD_MESSAGE = "samePasswordMessage";
    private static final String SAME_PASSWORD = "error.password";
    private static final String PATH_TO_CONFIRMATION_PAGE = ConfigManager.getInstance().getProperty("path.page.password.update.confirmation");
    private static final String PATH_TO_UPDATE_PAGE = ConfigManager.getInstance().getProperty("path.page.password.update");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String updateButton = request.getParameter(Constant.UPDATE_BUTTON);
        String currentPassword = request.getParameter(CURRENT_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);

        String language = session.getAttribute(Constant.LOCALE).toString();
        Locale locale = new Locale(language.substring(0,2));

        UserDAO userDAO = new UserDAO();

        if (updateButton != null) {
            String password = userDAO.findPasswordByLogin(user.getLogin());

            if (!verifyPassword(password, currentPassword)) {
                request.setAttribute(INCORRECT_PASSWORD_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(INCORRECT_PASSWORD));
                page = PATH_TO_UPDATE_PAGE;
            } else if (currentPassword.equals(newPassword)) {
                request.setAttribute(SAME_PASSWORD_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(SAME_PASSWORD));
                page = PATH_TO_UPDATE_PAGE;
            } else {
                updateUserPassword(user, newPassword);
                page = PATH_TO_CONFIRMATION_PAGE;
            }
        } else {
            page = PATH_TO_UPDATE_PAGE;
        }
        return page;
    }

    private boolean verifyPassword(String databasePassword, String providedPassword) {
        boolean verified;

        String securedPassword = databasePassword.substring(SUBSTRING, databasePassword.length() - Constant.SALT);
        String salt = databasePassword.length() > Constant.SALT ? databasePassword.substring(databasePassword.length() - Constant.SALT) : databasePassword;
        verified = PasswordUtil.verifyUserPassword(providedPassword, securedPassword, salt);

        return verified;
    }

    private static void updateUserPassword(User user, String password) {
        String newPasswordInDB = generateNewSecuredPassword(password);
        user.setPassword(newPasswordInDB);

        UserDAO userDAO = new UserDAO();
        userDAO.updateUserPassword(user);
    }

    private static String generateNewSecuredPassword(String newPassword) {
        String password;
        String salt = PasswordUtil.getSalt(Constant.SALT);

        String securedPassword = PasswordUtil.generateSecurePassword(newPassword, salt);
        StringBuilder saltedSecuredPassword = new StringBuilder();
        saltedSecuredPassword.append(securedPassword).append(salt);
        password = saltedSecuredPassword.toString();

        return password;
    }
}
