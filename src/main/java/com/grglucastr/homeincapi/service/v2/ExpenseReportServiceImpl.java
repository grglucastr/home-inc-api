package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.util.DateUtils;
import com.grglucastr.model.ExpenseMonthlySummaryResponse;
import com.grglucastr.model.ExpenseSummary;
import com.grglucastr.model.ExpenseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ExpenseReportServiceImpl implements ExpenseReportService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IncomeService incomeService;

    @Override
    public ExpenseMonthlySummaryResponse generateSummaryReport(List<Expense> expenses, int year, int monthNo) {
        final String monthlyProgress = DateUtils.getMonthlyProgress(year, monthNo);
        final ExpenseMonthlySummaryResponse summaryResponse = new ExpenseMonthlySummaryResponse();

        summaryResponse.setCount(0);
        summaryResponse.setTotal(BigDecimal.ZERO);
        summaryResponse.setAverage(BigDecimal.ZERO);
        summaryResponse.setTotalPaid(BigDecimal.ZERO);
        summaryResponse.setTotalToPay(BigDecimal.ZERO);
        summaryResponse.setMonthlyIncome(BigDecimal.ZERO);
        summaryResponse.setMonthlyProgress(monthlyProgress);

        if(expenses.isEmpty())
            return summaryResponse;

        final Comparator<Expense> byCostComparing = Comparator.comparing(Expense::getCost);
        final Predicate<Expense> isPaid = Expense::isPaid;

        final Optional<Expense> max = expenses.stream().max(byCostComparing);
        final Optional<Expense> min = expenses.stream().min(byCostComparing);

        final Optional<BigDecimal> total = expenses.stream()
                .map(Expense::getCost)
                .reduce(BigDecimal::add);

        final Optional<BigDecimal> totalPaid = expenses.stream()
                .filter(isPaid)
                .map(Expense::getCost)
                .reduce(BigDecimal::add);

        final Optional<BigDecimal> totalToPay = expenses.stream()
                .filter(isPaid.negate())
                .map(Expense::getCost)
                .reduce(BigDecimal::add);

        final ExpenseSummary resMax = new ExpenseSummary();
        resMax.setValue(BigDecimal.ZERO);
        resMax.setExpense(null);

        if(max.isPresent()){
            final ExpenseResponse expRes = mapper.map(max.get(), ExpenseResponse.class);
            resMax.setExpense(expRes);
            resMax.setValue(expRes.getCost());
        }

        final ExpenseSummary resMin = new ExpenseSummary();
        resMin.setValue(BigDecimal.ZERO);
        resMin.setExpense(null);

        if(min.isPresent()){
            final ExpenseResponse expRes = mapper.map(min.get(), ExpenseResponse.class);
            resMin.setExpense(expRes);
            resMin.setValue(expRes.getCost());
        }

        summaryResponse.setCount(expenses.size());
        summaryResponse.setAverage(total
                .map(t -> t.divide(new BigDecimal(expenses.size()), RoundingMode.HALF_UP))
                .orElse(BigDecimal.ZERO));
        summaryResponse.setMax(resMax);
        summaryResponse.setMin(resMin);
        summaryResponse.setMonthlyProgress(monthlyProgress);
        summaryResponse.setTotal(total.orElse(BigDecimal.ZERO));
        summaryResponse.setTotalPaid(totalPaid.orElse(BigDecimal.ZERO));
        summaryResponse.setTotalToPay(totalToPay.orElse(BigDecimal.ZERO));

        final Optional<Income> income = incomeService.findByDateRange(year, monthNo);
        income.ifPresent(i -> summaryResponse.setMonthlyIncome(i.getAmount()));

        return summaryResponse;
    }
}
