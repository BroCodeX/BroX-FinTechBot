package brocodex.fbot.component;

import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.transactions.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class InitData implements ApplicationRunner {
    @Autowired
    private TransactionCategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initCategories();
    }

    public void initCategories() {
        List<String> categoriesList = List.of("salary", "bonuses", "investments", "other",
                "food and drink", "transport", "health and medicine", "entertainment", "shopping", "other expense");
        List<TransactionCategory> categories = categoriesList
                .stream()
                .map(i -> {
                    var cat = new TransactionCategory();
                    cat.setSlug(i);
                    return cat;
                })
                .toList();
        categories.forEach(cat -> {
            if(categoryRepository.findBySlug(cat.getSlug()).isEmpty()) {
                categoryRepository.save(cat);
            }
        });
    }
}
