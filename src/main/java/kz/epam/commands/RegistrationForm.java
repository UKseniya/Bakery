package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;
import kz.epam.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegistrationForm implements Command {
    private static final int SALT_LENGTH = 30;
    private static final String PHONE_NUMBER_REGEX = "\\d+";
    private static final String INCORRECT_PHONE = "error.phone";
    private static final String INCORRECT_PHONE_MESSAGE = "phoneNumberError";
    private static final String REGISTRATION_ERROR = "registrationErrorMessage";
    private static final String REGISTRATION_ERROR_MESSAGE = "error.registration";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/registration_successful.jsp";
    private static final String PATH_TO_REGISTRATION_PAGE = "/jsp/registration.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String firstName = request.getParameter(Constants.FIRST_NAME);
        String lastName = request.getParameter(Constants.LAST_NAME);
        String email = request.getParameter(Constants.EMAIL);
        String phone = request.getParameter(Constants.PHONE);
        String login = request.getParameter(Constants.LOGIN);
        String providedPassword = request.getParameter(Constants.PASSWORD);

        HttpSession session = request.getSession();

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        Pattern phoneNumberPattern =  Pattern.compile(PHONE_NUMBER_REGEX);

        UserDAO userDAO = new UserDAO();
        boolean isLoginFree = userDAO.isLoginFree(login);
        if (isLoginFree) {
            // Generate Salt. The generated value can be stored in DB.
            String salt = PasswordUtil.getSalt(SALT_LENGTH);

            // Protect user's providedPassword. The generated value can be stored in DB.
            String securedPassword = PasswordUtil.generateSecurePassword(providedPassword, salt);

            // Get the value of securedPassword and salt to be stored in DB
            StringBuilder saltedSecuredPassword = new StringBuilder();
            saltedSecuredPassword.append(securedPassword).append(salt);
            String password = saltedSecuredPassword.toString();

            if (phoneNumberPattern.matcher(phone).matches() && phone.length() == 11) {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setLogin(login);
                user.setPassword(password);

                userDAO.create(user);
                page = PATH_TO_CONFIRMATION_PAGE;
            }
            else {
                request.setAttribute(INCORRECT_PHONE_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(INCORRECT_PHONE));
                page = PATH_TO_REGISTRATION_PAGE;
            }
        }

        else {
            request.setAttribute(REGISTRATION_ERROR,
                    MessageManager.getInstance(locale).getProperty(REGISTRATION_ERROR_MESSAGE));
            page = PATH_TO_REGISTRATION_PAGE;
        }
        return page;
    }
}
