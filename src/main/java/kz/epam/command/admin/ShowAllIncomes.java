package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.dao.IncomeDAO;
import kz.epam.entity.Income;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowAllIncomes implements Command {

    private static final String ALL_INCOMES = "allIncomes";
    private static final String PATH_TO_REVIEW_ALL_INCOMES = ConfigManager.getInstance().getProperty("path.page.show.all.incomes");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();

        IncomeDAO incomeDAO = new IncomeDAO();
        List<Income> allIncomes = incomeDAO.findAll();
        session.setAttribute(ALL_INCOMES, allIncomes);

        page = PATH_TO_REVIEW_ALL_INCOMES;

        return page;
    }
}
