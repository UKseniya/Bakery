package kz.epam.command;

import kz.epam.config.ConfigManager;

import javax.servlet.http.HttpServletRequest;

public class GetLoginForm implements Command{

    private static final String PATH_TO_LOGIN_PAGE = ConfigManager.getInstance().getProperty("path.page.login");

    @Override
    public String execute(HttpServletRequest request) {
        return PATH_TO_LOGIN_PAGE;
    }
}
