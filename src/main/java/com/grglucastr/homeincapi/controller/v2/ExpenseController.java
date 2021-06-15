package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.api.ExpensesApi;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import com.grglucastr.model.ExpenseFilter;
import com.grglucastr.model.ExpenseMonthlySummaryResponse;
import com.grglucastr.model.ExpenseMonthlySummaryResponseMax;
import com.grglucastr.model.ExpenseMonthlySummaryResponseMin;
import com.grglucastr.model.ExpenseRequest;
import com.grglucastr.model.ExpenseResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController("ExpenseV2Controller")
@Slf4j
public class ExpenseController implements ExpensesApi {

    private ExpenseService expenseService;
    private ModelMapper mapper;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ModelMapper modelMapper) {
        this.expenseService = expenseService;
        this.mapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<ExpenseResponse>> getExpenses(ExpenseFilter filter) {
        final List<Expense> expenses = expenseService.findAll();
        final List<ExpenseResponse> expenseResponses = expenses.stream()
                .map(expense -> mapper.map(expense, ExpenseResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(expenseResponses);
    }

    @Override
    public ResponseEntity<ExpenseResponse> postExpenses(ExpenseRequest expenseRequest) {
        final Expense expense = mapper.map(expenseRequest, Expense.class);
        final Expense expenseResponse = expenseService.save(expense);
        final ExpenseResponse response = mapper.map(expenseResponse, ExpenseResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ExpenseResponse> payExpense(Long expenseId) {

        final Optional<Expense> optExpense = expenseService.findById(expenseId);
        if(optExpense.isEmpty())
            return ResponseEntity.notFound().build();

        final Expense expense = optExpense.get();
        return payExpense(expense);
    }

    @Override
    public ResponseEntity<ExpenseResponse> getExpenseById(Long expenseId) {
        final Optional<Expense> optExpense = expenseService.findById(expenseId);
        if (optExpense.isEmpty())
            return ResponseEntity.notFound().build();

        final Expense expense = optExpense.get();
        final ExpenseResponse response = mapper.map(expense, ExpenseResponse.class);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> invalidateExpense(Long expenseId) {
        final Optional<Expense> optExpense = expenseService.findById(expenseId);
        if (optExpense.isEmpty())
            return ResponseEntity.notFound().build();

        final Expense expense = optExpense.get();
        expense.setIsActive(false);
        expenseService.save(expense);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ExpenseMonthlySummaryResponse> monthlySummary(Integer monthNo, Boolean paid) {

        List<Expense> expenses;
        if(Optional.ofNullable(paid).isPresent()){
            expenses = expenseService.findByMonthAndPaid(monthNo, paid);
            final ExpenseMonthlySummaryResponse summaryResponse = generateSummaryReport(expenses, monthNo);
            return ResponseEntity.ok(summaryResponse);
        }

        expenses = expenseService.findByMonth(monthNo);
        final ExpenseMonthlySummaryResponse summaryResponse = generateSummaryReport(expenses, monthNo);
        return ResponseEntity.ok(summaryResponse);
    }

    private ExpenseMonthlySummaryResponse generateSummaryReport(List<Expense> expenses, int monthNo) {

        final String monthlyProgress = getMonthlyProgress(monthNo);
        final ExpenseMonthlySummaryResponse summaryResponse = new ExpenseMonthlySummaryResponse();
        summaryResponse.setMonthlyProgress(monthlyProgress);
        summaryResponse.setTotal(BigDecimal.ZERO);
        summaryResponse.setTotalPaid(BigDecimal.ZERO);
        summaryResponse.setTotalToPay(BigDecimal.ZERO);

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

        final ExpenseMonthlySummaryResponseMax resMax = new ExpenseMonthlySummaryResponseMax();
        resMax.setValue(BigDecimal.ZERO);
        resMax.setExpense(null);

        if(max.isPresent()){
            final ExpenseResponse expRes = mapper.map(max.get(), ExpenseResponse.class);
            resMax.setExpense(expRes);
            resMax.setValue(expRes.getCost());
        }

        final ExpenseMonthlySummaryResponseMin resMin = new ExpenseMonthlySummaryResponseMin();
        resMin.setValue(BigDecimal.ZERO);
        resMin.setExpense(null);

        if(min.isPresent()){
            final ExpenseResponse expRes = mapper.map(min.get(), ExpenseResponse.class);
            resMin.setExpense(expRes);
            resMin.setValue(expRes.getCost());
        }

        summaryResponse.setMax(resMax);
        summaryResponse.setMin(resMin);
        summaryResponse.setMonthlyProgress(monthlyProgress);
        summaryResponse.setTotal(total.orElse(BigDecimal.ZERO));
        summaryResponse.setTotalPaid(totalPaid.orElse(BigDecimal.ZERO));
        summaryResponse.setTotalToPay(totalToPay.orElse(BigDecimal.ZERO));

        return summaryResponse;
    }

    private String getMonthlyProgress(int monthNo){

        LocalDate now = LocalDate.now();
        if (now.getMonthValue() > monthNo) {
            return "100%";
        }

        if (now.getMonthValue() < monthNo) {
            return "0%";
        }

        int year = LocalDate.now().getYear();
        LocalDate start = LocalDate.of(year, monthNo, 1);
        final int lengthOfMonth = start.lengthOfMonth();

        double total = (now.getDayOfMonth() * 100.0) / lengthOfMonth;
        return Math.round(total * 100.0) / 100.0 + "%";
    }

    private ResponseEntity<ExpenseResponse> payExpense(Expense expense){
        expense.setPaid(true);
        expense.setPaidDate(LocalDate.now());
        expenseService.save(expense);

        final ExpenseResponse response = mapper.map(expense, ExpenseResponse.class);
        return ResponseEntity.ok(response);
    }
}
