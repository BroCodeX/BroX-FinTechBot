package brocodex.fbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(FbotApplication.class, args);
	}

}
