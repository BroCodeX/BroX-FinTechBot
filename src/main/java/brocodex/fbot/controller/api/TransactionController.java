package brocodex.fbot.controller.api;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionDTO>> getAll(@RequestParam(defaultValue = "5") int limit) {
        List<TransactionDTO> dtos = service.getAllTransactions(limit);
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO create(@RequestBody TransactionDTO dto) {
        return service.createTransaction(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO show(@PathVariable Long id) {
        return service.showTransaction(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDTO update(@PathVariable Long id, @RequestBody TransactionDTO dto) {
        return service.updateTransaction(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        service.destroyTransaction(id);
    }
}
