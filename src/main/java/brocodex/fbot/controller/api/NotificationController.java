package brocodex.fbot.controller.api;

import brocodex.fbot.dto.notification.NotificationDTO;
import brocodex.fbot.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<NotificationDTO>> getAll(@RequestParam(defaultValue = "5") int limit) {
        List<NotificationDTO> dtos = service.getAllNotifications(limit);
        return null;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDTO show(@PathVariable Long id) {
        return service.showNotification(id);
    }
}
