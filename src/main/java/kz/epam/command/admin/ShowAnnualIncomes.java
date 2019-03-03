package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.constant.Constants;
import kz.epam.dao.IncomeDAO;
import kz.epam.entities.Income;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ShowAnnualIncomes implements Command {

    private static final String ANNUAL_INCOMES = "annualIncomes";
    private static final String PATH_TO_REVIEW_ANNUAL_INCOMES = "/jsp/admin/show_annual_incomes.jsp";
    private List<Income> annualIncomes;
    private int year;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        LocalDate today = LocalDate.now();
        year = today.getYear();

        IncomeDAO incomeDAO = new IncomeDAO();

        annualIncomes = incomeDAO.findIncomesForYear(year, locale);

        session.setAttribute(ANNUAL_INCOMES, annualIncomes);

        page = PATH_TO_REVIEW_ANNUAL_INCOMES;

        return page;
    }
}
