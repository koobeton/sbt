package ru.sbt.bit.ood.solid.homework.db;

import ru.sbt.bit.ood.solid.homework.utils.DateRange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService {

    private final Connection connection;

    public DBService(Connection connection) {
        this.connection = connection;
    }

    public List<EmployeeSalary> executeQuery(String departmentId, DateRange range) {
        try {
            // prepare statement with sql
            PreparedStatement ps = prepareStatement();
            // inject parameters to sql
            injectParameters(ps, departmentId, range);
            // execute query and get the results
            return getResults(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Database exception: " + e.getMessage(), e);
        }
    }

    private PreparedStatement prepareStatement() throws SQLException {
        return connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
    }

    private void injectParameters(PreparedStatement ps, String departmentId, DateRange range) throws SQLException {
        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(range.from().toEpochDay()));
        ps.setDate(2, new java.sql.Date(range.to().toEpochDay()));
    }

    private List<EmployeeSalary> getResults(ResultSet resultSet) throws SQLException {
        List<EmployeeSalary> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(new EmployeeSalary(resultSet.getString("emp_name"), resultSet.getDouble("salary")));
        }
        return results;
    }
}
