package kz.epam.command;

import kz.epam.constant.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.regex.Pattern;

public class UpdateUserDetails implements Command {

    private static final String UPDATE_BUTTON = "updateButton";
    private static final String PHONE_NUMBER_REGEX = "\\d+";
    private static final String INCORRECT_PHONE = "error.phone";
    private static final String INCORRECT_PHONE_MESSAGE = "phoneNumberError";
    private static final String PATH_TO_CONFIRMATION_PAGE = "/jsp/details_updated.jsp";
    private static final String PATH_TO_UPDATE_PAGE = "/jsp/user_details_update.jsp";

    //    TODO: split the whole method into several small methods?
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String updateButton = request.getParameter(UPDATE_BUTTON);
        String firstName = request.getParameter(Constants.FIRST_NAME);
        String lastName = request.getParameter(Constants.LAST_NAME);
        String email = request.getParameter(Constants.EMAIL);
        String phone = request.getParameter(Constants.PHONE);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        if (updateButton != null) {
            Pattern phoneNumberPattern = Pattern.compile(PHONE_NUMBER_REGEX);
            UserDAO userDAO = new UserDAO();
            if (phoneNumberPattern.matcher(phone).matches() && phone.length() == 11) {
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
        }
        else {
            page = PATH_TO_UPDATE_PAGE;
        }

        return page;
    }
}
