package bg.softuni.invoice_app.model.dto;

import bg.softuni.invoice_app.validation.report.ValidDateRange;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@ValidDateRange
public class ReportCriteria {
    //todo add message for not null
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public ReportCriteria setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public ReportCriteria setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
}
