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

import java.util.logging.Logger;

@SpringBootApplication
@EnableJpaAuditing

public class FbotApplication {
	private static final Logger logger = Logger.getLogger(FbotApplication.class.getName());

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(FbotApplication.class, args);
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			FinanceBot bot = ctx.getBean(FinanceBot.class, AbilityBot.class);
			botsApi.registerBot(bot);
		} catch (TelegramApiException ex) {
			logger.severe("Failed to register the bot: " + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
