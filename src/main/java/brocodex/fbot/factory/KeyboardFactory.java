package brocodex.fbot.factory;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class KeyboardFactory {
    public static ReplyKeyboard getTransactionTypes() {
        KeyboardRow row = new KeyboardRow();
        row.add("income");
        row.add("expense");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getIncomeCategories() {
        KeyboardRow row = new KeyboardRow();
        row.add("Salary");
        row.add("Bonuses");
        row.add("Freelance");
        row.add("Investments");
        row.add("Other income");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getExpenseCategories() {
        KeyboardRow row = new KeyboardRow();
        row.add("Food & Drinks");
        row.add("Housing & Utilities");
        row.add("Transportation");
        row.add("Health & Medical");
        row.add("Entertainment");
        row.add("Electronic");
        row.add("Other expense");
        return new ReplyKeyboardMarkup(List.of(row));
    }
}
