package kz.epam.command;

import javax.servlet.http.HttpServletRequest;

public class NoCommand implements Command {

    private static final String MAIN_PAGE = "/index.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = MAIN_PAGE;
        return page;
    }
}
