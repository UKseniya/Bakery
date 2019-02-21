//package kz.epam.commands;
//
//import kz.epam.constants.Constants;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//public class LocaleSwitch implements Command {
//    private static final String REFER = "referer";
//    private static final String SLASH = "/";
//
//    @Override
//    public String execute(HttpServletRequest request) {
//        String page = null;
//        HttpSession session = request.getSession();
//
//        String language = request.getParameter(Constants.LANGUAGE);
//
//        session.setAttribute(Constants.LANGUAGE, language);
//
//            String referer = request.getHeader(REFER);
//            String[] uriNames = referer.split(SLASH);
//            String command = uriNames[uriNames.length - 1];
//            StringBuilder jspPage = new StringBuilder();
//            String jspPageName = jspPage.append(SLASH).append(command).toString();
//            page = jspPageName;
//
//            String[] commandSplit = command.split("=");
//            String controllerCommand = commandSplit[commandSplit.length -1];
//
//
//        page = CommandFactory.setCommand(controllerCommand.toUpperCase());
//
//        return page;
//    }
//}
