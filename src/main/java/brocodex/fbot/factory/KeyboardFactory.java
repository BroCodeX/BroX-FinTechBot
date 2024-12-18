package brocodex.fbot.factory;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup getTransactionTypes() {

        InlineKeyboardButton incomeButton = new InlineKeyboardButton("Income");
        InlineKeyboardButton expenseButton = new InlineKeyboardButton("Expense");

        incomeButton.setCallbackData("income");
        expenseButton.setCallbackData("expense");

        InlineKeyboardRow row = new InlineKeyboardRow(incomeButton, expenseButton);

        List<InlineKeyboardRow> keyboard = List.of(row);

        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup getIncomeCategories() {
        InlineKeyboardButton salary = new InlineKeyboardButton("Salary");
        salary.setCallbackData("salary");
        InlineKeyboardButton bonuses = new InlineKeyboardButton("Bonuses");
        bonuses.setCallbackData("bonuses");
        InlineKeyboardButton invest = new InlineKeyboardButton("Investments");
        invest.setCallbackData("investments");
        InlineKeyboardButton others = new InlineKeyboardButton("Other income");
        others.setCallbackData("other");

        InlineKeyboardRow row = new InlineKeyboardRow(salary, bonuses);
        InlineKeyboardRow row2 = new InlineKeyboardRow(invest, others);

        List<InlineKeyboardRow> keyboard = List.of(row, row2);

        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup getExpenseCategories() {
        InlineKeyboardButton foodrink = new InlineKeyboardButton("Food & Drinks");
        foodrink.setCallbackData("food and drink");
        InlineKeyboardButton transportation = new InlineKeyboardButton("Transportation");
        transportation.setCallbackData("transport");
        InlineKeyboardButton health = new InlineKeyboardButton("Health & Medical");
        health.setCallbackData("health and medicine");
        InlineKeyboardButton entertainment = new InlineKeyboardButton("Entertainment");
        entertainment.setCallbackData("entertainment");
        InlineKeyboardButton shopping = new InlineKeyboardButton("Shopping");
        shopping.setCallbackData("shopping");
        InlineKeyboardButton others = new InlineKeyboardButton("Other expense");
        others.setCallbackData("other expense");

        InlineKeyboardRow row = new InlineKeyboardRow(foodrink, transportation);
        InlineKeyboardRow row1 = new InlineKeyboardRow(health,  entertainment);
        InlineKeyboardRow row2 = new InlineKeyboardRow(shopping, others);

        List<InlineKeyboardRow> keyboard = List.of(row, row1, row2);

        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup getDataFilters() {
        var now = LocalDateTime.now();
        InlineKeyboardButton lastWeek = new InlineKeyboardButton("Last Week");
        InlineKeyboardButton lastTwoWeeks = new InlineKeyboardButton("Last Two Weeks");
        InlineKeyboardButton lastMonth = new InlineKeyboardButton("Last Month");
        InlineKeyboardButton allTime = new InlineKeyboardButton("All time");

        lastWeek.setCallbackData(now.minusWeeks(1).toString());
        lastTwoWeeks.setCallbackData(now.minusWeeks(2).toString());
        lastMonth.setCallbackData(now.minusMonths(1).toString());
        allTime.setCallbackData(now.minusYears(100).toString());

        InlineKeyboardRow row = new InlineKeyboardRow(lastWeek, lastTwoWeeks);
        InlineKeyboardRow row2 = new InlineKeyboardRow(lastMonth, allTime);

        List<InlineKeyboardRow> keyboard = List.of(row, row2);

        return new InlineKeyboardMarkup(keyboard);
    }

    public static InlineKeyboardMarkup getTypeFilters() {

        InlineKeyboardButton incomeButton = new InlineKeyboardButton("Income");
        InlineKeyboardButton expenseButton = new InlineKeyboardButton("Expense");
        InlineKeyboardButton bothButton = new InlineKeyboardButton("Both types");

        incomeButton.setCallbackData("income");
        expenseButton.setCallbackData("expense");
        bothButton.setCallbackData("both");

        InlineKeyboardRow row = new InlineKeyboardRow(incomeButton, expenseButton);
        InlineKeyboardRow row2 = new InlineKeyboardRow(bothButton);

        List<InlineKeyboardRow> keyboard = List.of(row, row2);

        return new InlineKeyboardMarkup(keyboard);
    }

}
