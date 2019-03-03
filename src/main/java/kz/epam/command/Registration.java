package kz.epam.command;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {

    private static final String PATH_TO_REGISTRATION_PAGE = "/jsp/registration.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = PATH_TO_REGISTRATION_PAGE;
        return page;
    }
}
