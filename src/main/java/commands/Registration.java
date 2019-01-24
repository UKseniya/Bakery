package commands;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = "/jsp/registration.jsp";
        return page;
    }
}
