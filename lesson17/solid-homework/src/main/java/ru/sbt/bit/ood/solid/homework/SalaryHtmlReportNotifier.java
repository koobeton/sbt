package ru.sbt.bit.ood.solid.homework;

import ru.sbt.bit.ood.solid.homework.db.DBService;
import ru.sbt.bit.ood.solid.homework.db.EmployeeSalary;
import ru.sbt.bit.ood.solid.homework.html.HtmlBuilder;
import ru.sbt.bit.ood.solid.homework.mail.MailService;
import ru.sbt.bit.ood.solid.homework.utils.DateRange;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class SalaryHtmlReportNotifier {

    private final Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        List<EmployeeSalary> employeeSalaryList = new DBService(connection).executeQuery(departmentId, new DateRange(dateFrom, dateTo));
        String report = new HtmlBuilder().build(employeeSalaryList);
        new MailService().sendMail(recipients, report);
    }
}
