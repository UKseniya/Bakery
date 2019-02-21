package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;
import kz.epam.util.PasswordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class GetLoginForm implements Command{
    private static final String PATH_TO_LOGIN_PAGE = "/jsp/login.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = PATH_TO_LOGIN_PAGE;
        return page;
    }
}
