package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.model.SingleSummaryResponse;

import java.util.List;

public interface ExpenseReportService {

    SingleSummaryResponse generateSingleSummaryReport(List<Expense> expenses, int year, int monthNo);

}
