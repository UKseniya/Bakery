package kz.epam.command;

import kz.epam.config.ConfigManager;

import javax.servlet.http.HttpServletRequest;

public class NoCommand implements Command {

    private static final String MAIN_PAGE = ConfigManager.getInstance().getProperty("path.page.index");

    @Override
    public String execute(HttpServletRequest request) {
        return MAIN_PAGE;
    }
}
