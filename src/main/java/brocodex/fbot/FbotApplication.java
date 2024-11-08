package brocodex.fbot;

import brocodex.fbot.component.FinanceBot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FbotApplication {

	@Autowired
	private FinanceBot bot;

	public static void main(String[] args) {
		SpringApplication.run(FbotApplication.class, args);
	}

	@PostConstruct
	public void init() {
		bot.init();
	}

}
