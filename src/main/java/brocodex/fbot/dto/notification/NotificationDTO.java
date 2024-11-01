package brocodex.fbot.dto.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationDTO {
    private String alertMessage; // Сообщение о превышении бюджета
    private Double amountExceeded; // На сколько превышен бюджет
    private String typeAmount; // какой вид бюджета: общий, месячный или недельный
    private LocalDate notificationDate;
}
