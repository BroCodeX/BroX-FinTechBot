package brocodex.fbot;

import brocodex.fbot.component.FinanceBot;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableJpaAuditing
public class FbotApplication {

//	@Autowired
//	private FinanceBot bot;

	public static void main(String[] args) {
		//SpringApplication.run(FbotApplication.class, args);
		ConfigurableApplicationContext ctx = SpringApplication.run(FbotApplication.class, args);
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(ctx.getBean("FinanesBot", AbilityBot.class));
		} catch (TelegramApiException ex) {
			throw new RuntimeException();
		}
	}
}
