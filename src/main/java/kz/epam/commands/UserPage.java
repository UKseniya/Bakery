package kz.epam.commands;

import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserPage implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user.getRole().equals("user")) {
            page = "/jsp/user/user.jsp";
        }
        else if (user.getRole().equals("kz/epam/admin")){
            page = "/jsp/kz.epam.admin/kz.epam.admin.jsp";
        }

        return page;
    }
}
