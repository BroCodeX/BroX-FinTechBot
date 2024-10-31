package brocodex.fbot.controller.api;

import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetDTO show(@PathVariable Long id) {
        return service.showBudget(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetDTO create(@RequestBody UserDTO dto) {
        return service.createBudget(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetDTO update(@PathVariable Long id, @RequestBody BudgetDTO dto) {
        return service.updateBudget(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        service.destroyBudget(id);
    }
}
