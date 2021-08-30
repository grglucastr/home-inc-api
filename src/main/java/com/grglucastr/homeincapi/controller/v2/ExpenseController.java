package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.api.ExpensesApi;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.v2.ExpenseReportService;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import com.grglucastr.model.ExpenseFilter;
import com.grglucastr.model.ExpenseMonthlySummaryResponse;
import com.grglucastr.model.ExpenseRequest;
import com.grglucastr.model.ExpenseResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("ExpenseV2Controller")
@Slf4j
public class ExpenseController implements ExpensesApi {

    private static final String PAY = "pay";
    public static final String MARK_AS_PAID = "Mark as Paid";
    private ExpenseReportService expenseReportService;
    private ExpenseService expenseService;
    private ModelMapper mapper;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ExpenseReportService expenseReportService, ModelMapper modelMapper) {
        this.expenseService = expenseService;
        this.expenseReportService = expenseReportService;
        this.mapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<ExpenseResponse>> getExpenses(ExpenseFilter filter) {
        List<Expense> expenses = expenseService.findAll();

        expenses = filterExpenses(filter, expenses);

        final List<ExpenseResponse> expenseResponses = expenses.stream()
                .filter(isActive(filter).or(isPaid(filter)))
                .map(expense -> mapper.map(addPayLinkToExpense(expense), ExpenseResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(expenseResponses);
    }

    @Override
    public ResponseEntity<ExpenseResponse> postExpenses(ExpenseRequest expenseRequest) {
        final Expense expense = mapper.map(expenseRequest, Expense.class);
        final Expense expenseResponse = expenseService.save(expense);
        final ExpenseResponse response = mapper.map(addPayLinkToExpense(expenseResponse), ExpenseResponse.class);
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

        addPayLinkToExpense(expense);

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
    public ResponseEntity<ExpenseMonthlySummaryResponse> getReportSummaryByYearAndMonth(Integer year, Integer month, Boolean paid) {
        List<Expense> expenses;
        if(Optional.ofNullable(paid).isPresent()){
            expenses = expenseService.findByMonthAndYearAndPaid(year, month, paid);
            final ExpenseMonthlySummaryResponse summaryResponse = expenseReportService.generateSummaryReport(expenses, year, month);
            return ResponseEntity.ok(summaryResponse);
        }

        expenses = expenseService.findByMonthAndYear(year, month);
        final ExpenseMonthlySummaryResponse summaryResponse = expenseReportService.generateSummaryReport(expenses, year, month);
        return ResponseEntity.ok(summaryResponse);
    }

    @Override
    public ResponseEntity<ExpenseResponse> patchExpenses(Long expenseId, ExpenseRequest request) {
        final Optional<Expense> optExpense = expenseService.findById(expenseId);
        if (optExpense.isEmpty())
            return ResponseEntity.notFound().build();

        final Expense expense = optExpense.get();
        final Field[] reqFields = request.getClass().getDeclaredFields();

        for (Field reqField : reqFields){
            try {
                reqField.setAccessible(true);
                final Object value = reqField.get(request);

                if (value != null) {
                    final Field field = expense.getClass().getDeclaredField(reqField.getName());
                    field.setAccessible(true);
                    if (field.getType().isPrimitive()){
                        field.set(expense, Boolean.TRUE.equals(value));
                    } else {
                        field.set(expense, field.getType().cast(value));
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        final Expense expenseResponse = expenseService.save(expense);
        final ExpenseResponse response = mapper.map(addPayLinkToExpense(expenseResponse), ExpenseResponse.class);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ExpenseResponse> payExpense(Expense expense){

        if(expense.isPaid()){
            return ResponseEntity.ok(mapper.map(expense, ExpenseResponse.class));
        }

        expense.setPaid(true);
        expense.setPaidDate(LocalDate.now());
        expenseService.save(expense);

        final ExpenseResponse response = mapper.map(expense, ExpenseResponse.class);
        return ResponseEntity.ok(response);
    }

    private List<Expense> filterExpenses(ExpenseFilter filter, List<Expense> expenses) {
        if(filter.getActive() != null){
            expenses = expenses
                    .stream()
                    .filter(isActive(filter))
                    .collect(Collectors.toList());
        }

        if(filter.getPaid() != null){
            expenses = expenses
                    .stream()
                    .filter(isPaid(filter))
                    .collect(Collectors.toList());
        }

        if(filter.getPeriodicity() != null){
            expenses = expenses
                    .stream()
                    .filter(e -> e.getPeriodicity().toString().equalsIgnoreCase(filter.getPeriodicity()))
                    .collect(Collectors.toList());
        }

        if(filter.getPaymentMethod() != null){
            expenses = expenses
                    .stream()
                    .filter(e -> e.getPaymentMethod().toString().equalsIgnoreCase(filter.getPaymentMethod()))
                    .collect(Collectors.toList());
        }

        if(filter.getDueDateStart() != null){
            expenses = expenses
                    .stream()
                    .filter(e -> e.getDueDate().isAfter(filter.getDueDateStart().minusDays(1)))
                    .collect(Collectors.toList());
        }

        if(filter.getDueDateEnd() != null){
            expenses = expenses
                    .stream()
                    .filter(e -> e.getDueDate().isBefore(filter.getDueDateEnd().plusDays(1)))
                    .collect(Collectors.toList());
        }
        return expenses;
    }

    private Predicate<Expense> isPaid(ExpenseFilter filter) {
        return e -> filter.getPaid() != null && e.isPaid() == filter.getPaid();
    }

    private Predicate<Expense> isActive(ExpenseFilter filter) {
        return e -> filter.getActive() != null && e.getIsActive() == filter.getActive();
    }

    private Expense addPayLinkToExpense(Expense expense) {

        final Link link = linkTo(methodOn(ExpensesApi.class)
                ._payExpense(expense.getId()))
                .withSelfRel()
                .withTitle(MARK_AS_PAID);

        expense.add(link);

        return expense;
    }
}
