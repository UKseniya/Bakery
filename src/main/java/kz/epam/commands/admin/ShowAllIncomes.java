package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.IncomeDAO;
import kz.epam.entities.Income;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ShowAllIncomes implements Command {
    private static final String ALL_INCOMES = "allIncomes";
    private static final String PATH_TO_REVIEW_ALL_INCOMES = "/jsp/admin/show_all_incomes.jsp";
    private List<Income> allIncomes;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constants.LOCALE).toString();

        IncomeDAO incomeDAO = new IncomeDAO();

        allIncomes = incomeDAO.findAll();

        session.setAttribute(ALL_INCOMES, allIncomes);

        page = PATH_TO_REVIEW_ALL_INCOMES;

        return page;
    }
}
