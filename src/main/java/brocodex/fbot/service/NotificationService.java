package brocodex.fbot.service;

import brocodex.fbot.dto.notification.NotificationDTO;
import brocodex.fbot.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;

    public List<NotificationDTO> getAllNotifications(int limit) {
        return null;
    }

    public NotificationDTO showNotification(Long id) {
        return null;
    }
}
