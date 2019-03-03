package kz.epam.command;

import kz.epam.constant.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;
import kz.epam.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class Login implements Command{

    private static final int SALT_LENGTH = 30;
    private static final String LOGIN_ERROR = "loginErrorMessage";
    private static final String ERROR_MESSAGE = "error.login";
    private static final String PATH_TO_LOGIN_PAGE = "/jsp/login.jsp";
    private static final String PATH_TO_ADMIN_PAGE = "/controller?command=admin_page";
    private static final String PATH_TO_USER_PAGE = "/controller?command=user_page";

    //    TODO: split the whole method into several small methods;
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String login = request.getParameter(Constants.LOGIN);
        String providedPassword = request.getParameter(Constants.PASSWORD);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        UserDAO userDAO = new UserDAO();

        if (user != null) {
            page = PATH_TO_USER_PAGE;
        }

        else if (login != "") {

            if (providedPassword.length() < SALT_LENGTH) {
                boolean isUserRegistered = userDAO.isUserRegistered(login, providedPassword);
                if (isUserRegistered) {
                    user = userDAO.findUserByLoginAndPassword(login, providedPassword);
                    // Generate Salt. The generated value can be stored in DB.
                    String salt = PasswordUtil.getSalt(SALT_LENGTH);

                    // Protect user's providedPassword. The generated value can be stored in DB.
                    String securedPassword = PasswordUtil.generateSecurePassword(providedPassword, salt);

                    // Get the value of securedPassword and salt to be stored in DB
                    StringBuilder saltedSecuredPassword = new StringBuilder();
                    saltedSecuredPassword.append(securedPassword).append(salt);
                    String password = saltedSecuredPassword.toString();

                    user.setPassword(password);

                    // Update user password in DB
                    userDAO.updateUserPassword(user);
                } else {
                    request.setAttribute(LOGIN_ERROR,
                            MessageManager.getInstance(locale).getProperty(ERROR_MESSAGE));
                    page = PATH_TO_LOGIN_PAGE;
                }

            }

            // Retrieve secured password and salt from the password stored in DB.
            String password = userDAO.findPasswordByLogin(login);
            String securedPassword = password.substring(0, password.length() - SALT_LENGTH);
            String salt = password.length() > SALT_LENGTH ? password.substring(password.length() - SALT_LENGTH) : password;


            // Verify password provided by user
            boolean passwordVerified = PasswordUtil.verifyUserPassword(providedPassword, securedPassword, salt);

            if (passwordVerified == true) {
                user = userDAO.findUserByLoginAndPassword(login, password);
                session.setAttribute(Constants.USER, user);

                if (user.getRole().equals(Constants.USER)) {
                    page = Constants.PATH_TO_USER_PAGE;
                } else if (user.getRole().equals(Constants.ADMIN)) {

                    page = PATH_TO_ADMIN_PAGE;
                }
                session.setAttribute(Constants.USER, user);
            } else {
                request.setAttribute(LOGIN_ERROR,
                        MessageManager.getInstance(locale).getProperty(ERROR_MESSAGE));
                page = PATH_TO_LOGIN_PAGE;
            }
        }  else {
            request.setAttribute(LOGIN_ERROR,
                    MessageManager.getInstance(locale).getProperty(ERROR_MESSAGE));
            page = PATH_TO_LOGIN_PAGE;
        }
        return page;
    }
}