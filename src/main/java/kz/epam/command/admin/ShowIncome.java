package kz.epam.command.admin;

import kz.epam.command.Command;
import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.dao.IncomeDAO;
import kz.epam.entity.Income;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Locale;

public class ShowIncome implements Command {

    private static final String CURRENT_MONTH_INCOME = "currentMonthIncome";
    private static final String PREVIOUS_MONTH_INCOME = "previousMonthIncome";
    private static final String PATH_TO_REVIEW_INCOMES = ConfigManager.getInstance().getProperty("path.page.show.incomes");

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = Locale.forLanguageTag(language.substring(0,2));

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int year = today.getYear();

        Income currentMonthIncome = findIncomeForMonth(currentMonth, year, locale);
        session.setAttribute(CURRENT_MONTH_INCOME, currentMonthIncome);

        int previousMonth;
        if (currentMonth == Constant.FIRST_MONTH) {
            previousMonth = Constant.LAST_MONTH;
            year = year - Constant.DECREMENT;
        } else {
            previousMonth = currentMonth - Constant.DECREMENT;
        }

        Income previousMonthIncome = findIncomeForMonth(previousMonth, year, locale);
        session.setAttribute(PREVIOUS_MONTH_INCOME, previousMonthIncome);

        page = PATH_TO_REVIEW_INCOMES;

        return page;
    }

    private static Income findIncomeForMonth (int month, int year, Locale locale) {
        IncomeDAO incomeDAO = new IncomeDAO();
        return incomeDAO.findIncomeForMonth(month, year, locale);
    }

}
