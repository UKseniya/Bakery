package kz.epam.command;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.UserDAO;
import kz.epam.entity.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.regex.Pattern;

public class UpdateUserDetails implements Command {

    private static final String INCORRECT_PHONE = "error.phone";
    private static final String INCORRECT_PHONE_MESSAGE = "phoneNumberError";
    private static final String PATH_TO_CONFIRMATION_PAGE = ConfigManager.getInstance().getProperty("path.page.user.info.update.confirmation");
    private static final String PATH_TO_UPDATE_PAGE = ConfigManager.getInstance().getProperty("path.page.user.info.update");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        String updateButton = request.getParameter(Constant.UPDATE_BUTTON);
        String firstName = request.getParameter(Constant.FIRST_NAME);
        String lastName = request.getParameter(Constant.LAST_NAME);
        String email = request.getParameter(Constant.EMAIL);
        String phone = request.getParameter(Constant.PHONE);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);

        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = new Locale(language.substring(0,2));

        if (updateButton != null) {
            Pattern phoneNumberPattern = Pattern.compile(Constant.PHONE_NUMBER_REGEX);
            UserDAO userDAO = new UserDAO();
            if (phoneNumberPattern.matcher(phone).matches() && phone.length() == Constant.PHONE_NUMBER_LENGTH) {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phone);

                userDAO.updateUserInfo(user);
                page = PATH_TO_CONFIRMATION_PAGE;
            } else {
                request.setAttribute(INCORRECT_PHONE_MESSAGE,
                        MessageManager.getInstance(locale).getProperty(INCORRECT_PHONE));
                page = PATH_TO_UPDATE_PAGE;
            }
        } else {
            page = PATH_TO_UPDATE_PAGE;
        }

        return page;
    }
}
