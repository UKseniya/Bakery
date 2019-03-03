package kz.epam.command;

import kz.epam.constant.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;
import kz.epam.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class UpdateUserPassword implements Command {

    private static final int SALT_LENGTH = 30;
    private static final String UPDATE_BUTTON = "updateButton";
    private static final String CURRENT_PASSWORD = "currentPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String INCORRECT_PASSWORD_MESSAGE = "incorrectPasswordMessage";
    private static final String INCORRECT_PASSWORD = "error.incorrect.password";
    private static final String SAME_PASSWORD_MESSAGE = "samePasswordMessage";
    private static final String SAME_PASSWORD = "error.password";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/password_updated.jsp";
    private static final String PATH_TO_UPDATE_PAGE = "/jsp/user_password_update.jsp";

    //    TODO: split the whole method into several small methods;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String updateButton = request.getParameter(UPDATE_BUTTON);
        String currentPassword = request.getParameter(CURRENT_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        UserDAO userDAO = new UserDAO();

        if (updateButton != null) {
            // Retrieve secured password and salt from the password stored in DB.
            String password = userDAO.findPasswordByLogin(user.getLogin());
            String currentSecuredPassword = password.substring(0, password.length() - SALT_LENGTH);
            String currentSalt = password.length() > SALT_LENGTH ? password.substring(password.length() - SALT_LENGTH) : password;


            // Verify password provided by user
            boolean passwordVerified = PasswordUtil.verifyUserPassword(currentPassword, currentSecuredPassword, currentSalt);

            String newSalt = PasswordUtil.getSalt(SALT_LENGTH);

            // Protect user's providedPassword. The generated value can be stored in DB.
            String newSecuredPassword = PasswordUtil.generateSecurePassword(newPassword, newSalt);

            // Get the value of securedPassword and salt to be stored in DB
            StringBuilder saltedSecuredPassword = new StringBuilder();
            saltedSecuredPassword.append(newSecuredPassword).append(newSalt);
            String newPasswordinDB = saltedSecuredPassword.toString();

            if (currentPassword.equals(newPassword)) {
                request.setAttribute(SAME_PASSWORD_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(SAME_PASSWORD));
                page = PATH_TO_UPDATE_PAGE;
            } else if (passwordVerified == false) {
                request.setAttribute(INCORRECT_PASSWORD_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(INCORRECT_PASSWORD));
                page = PATH_TO_UPDATE_PAGE;
            } else {
                user.setPassword(newPasswordinDB);
                userDAO.updateUserPassword(user);
                page = PATH_TO_CONFIRMATION_PAGE;
            }
        } else {
            page = PATH_TO_UPDATE_PAGE;
        }
        return page;
    }
}
