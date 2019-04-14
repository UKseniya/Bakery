package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.IncomeDAO;
import kz.epam.entity.Income;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ShowAnnualIncomes implements Command {

    private static final String ANNUAL_INCOMES = "annualIncomes";
    private static final String PATH_TO_REVIEW_ANNUAL_INCOMES = ConfigManager.getInstance().getProperty("path.page.show.annual.incomes");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constant.LOCALE).toString();
        Locale locale = new Locale(language.substring(0,2));

        LocalDate today = LocalDate.now();
        int year = today.getYear();

        IncomeDAO incomeDAO = new IncomeDAO();
        List<Income> annualIncomes = incomeDAO.findIncomesForYear(year, locale);
        session.setAttribute(ANNUAL_INCOMES, annualIncomes);

        page = PATH_TO_REVIEW_ANNUAL_INCOMES;

        return page;
    }
}
