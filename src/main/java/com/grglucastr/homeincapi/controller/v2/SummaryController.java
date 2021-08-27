package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.api.SummaryApi;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.v2.ExpenseReportService;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import com.grglucastr.model.SingleSummaryResponse;
import com.grglucastr.model.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SummaryController implements SummaryApi {

    private ExpenseService expenseService;
    private ExpenseReportService expenseReportService;

    @Autowired
    public SummaryController(ExpenseService expenseService, ExpenseReportService expenseReportService) {
        this.expenseService = expenseService;
        this.expenseReportService = expenseReportService;
    }

    @Override
    public ResponseEntity<SummaryResponse> getSummary() {

        final Map<String, String> summaryLinks = getSummaryLinks();

        final SummaryResponse summaryResponse = new SummaryResponse();
        summaryResponse.setCurrentMonth(summaryLinks.get("current"));
        summaryResponse.setNextMonth(summaryLinks.get("next"));
        summaryResponse.setPreviousMonth(summaryLinks.get("previous"));

        return ResponseEntity.ok(summaryResponse);
    }

    @Override
    public ResponseEntity<SingleSummaryResponse> getSingleSummary(Integer year, Integer month) {

        final List<Expense> expenses = expenseService.findByMonthAndYear(year, month);
        final SingleSummaryResponse response = expenseReportService.generateSingleSummaryReport(expenses, year, month);

        final Map<String, String> summaryLinks = getSummaryLinks(year, month);
        response.setPreviousMonth(summaryLinks.get("previous"));
        response.setNextMonth(summaryLinks.get("next"));

        return ResponseEntity.ok(response);
    }

    private Map<String, String> getSummaryLinks(){
        final int year = LocalDate.now().getYear();
        final int month = LocalDate.now().getMonthValue();
        return getSummaryLinks(year, month);

    }

    private Map<String, String> getSummaryLinks(Integer year, Integer month){
        final Map<String, String> links = new HashMap<>();

        final LocalDate paramDate = LocalDate.of(year, month, 1);

        final int prevMonth = paramDate.minusMonths(1L).getMonthValue();
        final int prevYear = paramDate.minusMonths(1L).getYear();

        final int currentMonth = paramDate.getMonthValue();
        final int currentYear = paramDate.getYear();

        final int nextMonth = paramDate.plusMonths(1L).getMonthValue();
        final int nextYear = paramDate.plusMonths(1L).getYear();

        final Link prevSummary = buildLink(prevYear, prevMonth);
        final Link currentSummary = buildLink(currentYear, currentMonth);
        final Link nextSummary = buildLink(nextYear, nextMonth);

        links.put("previous", prevSummary.getHref());
        links.put("current", currentSummary.getHref());
        links.put("next", nextSummary.getHref());

        return links;
    }

    private Link buildLink(int year, int month){
        final Link link = linkTo(methodOn(SummaryController.class)
                ._getSingleSummary(year, month))
                .withSelfRel();
        return link;
    }


}
