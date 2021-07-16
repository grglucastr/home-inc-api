package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.model.ExpenseMonthlySummaryResponse;

import java.util.List;

public interface ExpenseReportService {

    ExpenseMonthlySummaryResponse generateSummaryReport(List<Expense> expenses, int year, int monthNo);

}
