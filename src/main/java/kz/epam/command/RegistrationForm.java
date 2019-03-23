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
import java.util.regex.Pattern;

public class RegistrationForm implements Command {

    private static final int SALT_LENGTH = 30;
    private static final int PHONE_NUMBER_LENGTH = 11;
    private static final String PHONE_NUMBER_REGEX = "\\d+";
    private static final String INCORRECT_PHONE = "error.phone";
    private static final String INCORRECT_PHONE_MESSAGE = "phoneNumberError";
    private static final String REGISTRATION_ERROR = "registrationErrorMessage";
    private static final String REGISTRATION_ERROR_MESSAGE = "error.registration";
    private static final String PATH_TO_CONFIRMATION_PAGE = ConfigManager.getInstance().getProperty("path.page.registration.confirmation");
    private static final String PATH_TO_REGISTRATION_PAGE = ConfigManager.getInstance().getProperty("path.page.registration");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String firstName = request.getParameter(Constant.FIRST_NAME);
        String lastName = request.getParameter(Constant.LAST_NAME);
        String email = request.getParameter(Constant.EMAIL);
        String phone = request.getParameter(Constant.PHONE);
        String login = request.getParameter(Constant.LOGIN);
        String providedPassword = request.getParameter(Constant.PASSWORD);

        HttpSession session = request.getSession();

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language.substring(0,2));

        Pattern phoneNumberPattern = Pattern.compile(PHONE_NUMBER_REGEX);

        UserDAO userDAO = new UserDAO();
        boolean isLoginFree = userDAO.isLoginFree(login);
        if (isLoginFree) {

            String password = generateSecuredPassword(providedPassword);

            if (phoneNumberPattern.matcher(phone).matches() && phone.length() == PHONE_NUMBER_LENGTH) {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setLogin(login);
                user.setPassword(password);

                userDAO.create(user);
                page = PATH_TO_CONFIRMATION_PAGE;
            } else {
                request.setAttribute(INCORRECT_PHONE_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(INCORRECT_PHONE));
                page = PATH_TO_REGISTRATION_PAGE;
            }
        } else {
            request.setAttribute(REGISTRATION_ERROR,
                    MessageManager.getInstance(locale).getProperty(REGISTRATION_ERROR_MESSAGE));
            page = PATH_TO_REGISTRATION_PAGE;
        }
        return page;
    }

    private String generateSecuredPassword(String providedPassword) {
        String password;
        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtil.getSalt(SALT_LENGTH);

        // Protect user's providedPassword. The generated value can be stored in DB.
        String securedPassword = PasswordUtil.generateSecurePassword(providedPassword, salt);

        // Get the value of securedPassword and salt to be stored in DB
        StringBuilder saltedSecuredPassword = new StringBuilder();
        saltedSecuredPassword.append(securedPassword).append(salt);
        password = saltedSecuredPassword.toString();

        return password;
    }
}
