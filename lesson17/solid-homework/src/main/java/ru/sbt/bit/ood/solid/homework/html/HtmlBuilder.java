package ru.sbt.bit.ood.solid.homework.html;

import ru.sbt.bit.ood.solid.homework.db.EmployeeSalary;

import java.util.List;

public class HtmlBuilder {

    // create a StringBuilder holding a resulting html
    private final StringBuilder resultingHtml = new StringBuilder();

    public String build(List<EmployeeSalary> results) {
        appendHeader();
        appendTableBody(results);
        appendFooter();
        return resultingHtml.toString();
    }

    private void appendHeader() {
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
    }

    private void appendTableBody(List<EmployeeSalary> results) {
        double totals = 0;
        for (EmployeeSalary employeeSalary : results) {
            // process each row of query results
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(employeeSalary.getEmployee()).append("</td>"); // appending employee name
            resultingHtml.append("<td>").append(employeeSalary.getSalary()).append("</td>"); // appending employee salary for period
            resultingHtml.append("</tr>"); // add row end tag
            totals += employeeSalary.getSalary(); // add salary to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
    }

    private void appendFooter() {
        resultingHtml.append("</table></body></html>");
    }
}
