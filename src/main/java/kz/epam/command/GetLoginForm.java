package kz.epam.command;

import javax.servlet.http.HttpServletRequest;

public class GetLoginForm implements Command{

    private static final String PATH_TO_LOGIN_PAGE = "/jsp/login.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = PATH_TO_LOGIN_PAGE;
        return page;
    }
}
