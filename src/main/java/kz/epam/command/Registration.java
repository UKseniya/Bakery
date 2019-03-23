package kz.epam.command;

import kz.epam.config.ConfigManager;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {

    private static final String PATH_TO_REGISTRATION_PAGE = ConfigManager.getInstance().getProperty("path.page.registration");

    @Override
    public String execute(HttpServletRequest request) {
        return PATH_TO_REGISTRATION_PAGE;
    }
}
