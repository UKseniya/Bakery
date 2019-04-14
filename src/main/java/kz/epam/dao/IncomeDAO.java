package kz.epam.dao;

import kz.epam.constant.Constant;
import kz.epam.entity.Income;
import kz.epam.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class IncomeDAO extends AbstractDAO<Income> {

    private static final String SQL_FIND_ALL_INCOME_INFORMATION = "SELECT SUM(total_income) AS annual_income, year FROM income " +
            "GROUP BY year";
    private static final String SQL_FIND_INCOME_FOR_CERTAIN_MONTH = "SELECT * FROM income WHERE month = ? AND year = ?";
    private static final String SQL_FIND_INCOME_FOR_CERTAIN_YEAR = "SELECT * FROM income WHERE year = ?";

    private static final Logger LOG = Logger.getRootLogger();
    private static final String TOTAL_INCOME = "total_income";
    private static final String ANNUAL_INCOME = "annual_income";
    private static final String MONTH = "month";
    private static final String YEAR = "year";

    @Override
    public List<Income> findAll() {
        List<Income> incomes = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_INCOME_INFORMATION);) {
            incomes = new ArrayList<>();

            while (resultSet.next()) {
                Income income = new Income();
                income.setSum(resultSet.getDouble(ANNUAL_INCOME));
                income.setYear(resultSet.getInt(YEAR));
                incomes.add(income);
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        }
        finally {
            pool.releaseConnection(connection);
        }
        return incomes;
    }

    public Income findIncomeForMonth(int month, int year, Locale locale) {
        Income income = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_INCOME_FOR_CERTAIN_MONTH)) {
            preparedStatement.setInt(1, month);
            preparedStatement.setInt(2, year);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Calendar calendar = Calendar.getInstance();
                    int databaseMonth = resultSet.getInt(MONTH);
                    calendar.set(Calendar.MONTH, databaseMonth - 1);
                    String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, locale);
                    income = new Income();
                    income.setSum(resultSet.getDouble(TOTAL_INCOME));
                    income.setMonth(monthName);
                    income.setYear(resultSet.getInt(YEAR));

                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return income;
    }

    public List<Income> findIncomesForYear(int year, Locale locale) {
        List<Income> incomes = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_INCOME_FOR_CERTAIN_YEAR)) {
            preparedStatement.setInt(1, year);
            incomes = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Calendar calendar = Calendar.getInstance();
                    int databaseMonth = resultSet.getInt(MONTH);
                    calendar.set(Calendar.MONTH, databaseMonth - 1);
                    String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, locale);
                    Income income = new Income();
                    income.setSum(resultSet.getDouble(TOTAL_INCOME));
                    income.setMonth(monthName);
                    income.setYear(resultSet.getInt(YEAR));
                    incomes.add(income);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return incomes;
    }

    @Override
    public Income findEntityById(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean create(Income entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public Income update(Income entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }
}
