package kz.epam.command;

import kz.epam.constant.Constant;
import kz.epam.dao.UserDAO;
import kz.epam.entity.User;
import kz.epam.message.MessageManager;
import kz.epam.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class UpdateUserPassword implements Command {

    private static final int SALT_LENGTH = 30;
    private static final int SUBSTRING = 0;
    private static final String UPDATE_BUTTON = "updateButton";
    private static final String CURRENT_PASSWORD = "currentPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String INCORRECT_PASSWORD_MESSAGE = "incorrectPasswordMessage";
    private static final String INCORRECT_PASSWORD = "error.incorrect.password";
    private static final String SAME_PASSWORD_MESSAGE = "samePasswordMessage";
    private static final String SAME_PASSWORD = "error.password";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/password_updated.jsp";
    private static final String PATH_TO_UPDATE_PAGE = "/jsp/user_password_update.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        boolean passwordVerified = true;

        String updateButton = request.getParameter(UPDATE_BUTTON);
        String currentPassword = request.getParameter(CURRENT_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language);

        UserDAO userDAO = new UserDAO();

        if (updateButton != null) {
            // Retrieve secured password and salt from the password stored in DB.
            String password = userDAO.findPasswordByLogin(user.getLogin());

            // Verify password provided by user
            passwordVerified = verifyPassword(password, currentPassword);

            if (passwordVerified == false) {
                request.setAttribute(INCORRECT_PASSWORD_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(INCORRECT_PASSWORD));
                page = PATH_TO_UPDATE_PAGE;
            } else if (currentPassword.equals(newPassword)) {
                request.setAttribute(SAME_PASSWORD_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(SAME_PASSWORD));
                page = PATH_TO_UPDATE_PAGE;
            } else {
                // Get new secured password
                String newPasswordInDB = generateNewSecuredPassword(newPassword);

                user.setPassword(newPasswordInDB);
                userDAO.updateUserPassword(user);
                page = PATH_TO_CONFIRMATION_PAGE;
            }
        } else {
            page = PATH_TO_UPDATE_PAGE;
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

    private String generateNewSecuredPassword(String newPassword) {
        String password;
        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtil.getSalt(SALT_LENGTH);

        // Protect user's newPassword. The generated value can be stored in DB.
        String securedPassword = PasswordUtil.generateSecurePassword(newPassword, salt);

        // Get the value of securedPassword and salt to be stored in DB
        StringBuilder saltedSecuredPassword = new StringBuilder();
        saltedSecuredPassword.append(securedPassword).append(salt);
        password = saltedSecuredPassword.toString();

        return password;
    }
}
