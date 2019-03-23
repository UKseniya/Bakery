package kz.epam.command;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Logout implements Command {

    private static final String PATH_TO_WELCOME_PAGE = ConfigManager.getInstance().getProperty("path.page.index");

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.removeAttribute(Constant.USER);

        return PATH_TO_WELCOME_PAGE;
    }
}
