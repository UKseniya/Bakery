package commands;

import javax.servlet.http.HttpServletRequest;

public class Logout implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        String page = "/index.jsp";
        // remove session
        request.getSession().invalidate();
        return page;
    }
}
