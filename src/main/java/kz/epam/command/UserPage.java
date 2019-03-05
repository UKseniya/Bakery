package kz.epam.command;

import kz.epam.constant.Constant;
import kz.epam.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserPage implements Command {

    private static final String PATH_TO_ADMIN_PAGE = "/controller?command=admin_page";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER);

        if (user.getRole().equals(Constant.USER)) {
            page = Constant.PATH_TO_USER_PAGE;
        } else if (user.getRole().equals(Constant.ADMIN)) {
            page = PATH_TO_ADMIN_PAGE;
        }

        return page;
    }
}
