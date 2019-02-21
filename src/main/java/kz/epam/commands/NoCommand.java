package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;

public class NoCommand implements Command {
    private static final String MAIN_PAGE = "/index.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = MAIN_PAGE;
        return page;
    }
}
