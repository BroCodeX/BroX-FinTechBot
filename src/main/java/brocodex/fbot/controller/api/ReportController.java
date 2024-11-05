package brocodex.fbot.controller.api;

import brocodex.fbot.dto.transaction.report.TransactionReportDTO;
import brocodex.fbot.dto.transaction.report.TransactionFilterDTO;
import brocodex.fbot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TransactionReportDTO show(@ModelAttribute TransactionFilterDTO dto) {
        return service.showReport(dto);
    }
}
