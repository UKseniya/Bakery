package kz.epam.commands;

import javax.servlet.http.HttpServletRequest;

public class Logout implements Command {
    private static final String PATH_TO_WELCOME_PAGE = "/index.jsp";
    @Override
    public String execute(HttpServletRequest request) {

        String page = PATH_TO_WELCOME_PAGE;
        // remove session
        request.getSession().invalidate();
        return page;
    }
}
