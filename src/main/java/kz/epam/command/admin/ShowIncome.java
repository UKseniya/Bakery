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

    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;
    private static final int DECREMENT = 1;
    private static final String CURRENT_MONTH_INCOME = "currentMonthIncome";
    private static final String PREVIOUS_MONTH_INCOME = "previousMonthIncome";
    private static final String PATH_TO_REVIEW_INCOMES = ConfigManager.getInstance().getProperty("path.page.show.incomes");

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constant.LOCALE).toString();

        Locale locale = Locale.forLanguageTag(language.substring(0,2));

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int year = today.getYear();

        IncomeDAO incomeDAO = new IncomeDAO();

        Income currentMonthIncome = incomeDAO.findIncomeForMonth(currentMonth, year, locale);

        session.setAttribute(CURRENT_MONTH_INCOME, currentMonthIncome);

        int previousMonth;
        if (currentMonth == FIRST_MONTH) {
            previousMonth = LAST_MONTH;
            year = year - DECREMENT;
        } else {
            previousMonth = currentMonth - DECREMENT;
        }

        Income previousMonthIncome = incomeDAO.findIncomeForMonth(previousMonth, year, locale);

        session.setAttribute(PREVIOUS_MONTH_INCOME, previousMonthIncome);

        page = PATH_TO_REVIEW_INCOMES;

        return page;
    }

}
