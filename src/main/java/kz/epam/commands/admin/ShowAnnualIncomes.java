package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.IncomeDAO;
import kz.epam.entities.Income;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowAnnualIncomes implements Command {
    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;
    private static final int DECREMENT = 1;
    private static final String ANNUAL_INCOMES = "annualIncomes";
    private static final String PREVIOUS_MONTH_INCOME = "previousMonthIncome";
    private static final String PATH_TO_REVIEW_ANNUAL_INCOMES = "/jsp/admin/show_annual_incomes.jsp";
    private List<Income> annualIncomes;
    private Income previousMonthIncome;
    private int currentMonth;
    private int year;
    private int previousMonth;
    private int previousYear;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        LocalDate today = LocalDate.now();
//        currentMonth = today.getMonthValue();
        year = today.getYear();

        IncomeDAO incomeDAO = new IncomeDAO();

        annualIncomes = incomeDAO.findIncomesForYear(year, locale);

        session.setAttribute(ANNUAL_INCOMES, annualIncomes);

        page = PATH_TO_REVIEW_ANNUAL_INCOMES;

        return page;
    }
}
