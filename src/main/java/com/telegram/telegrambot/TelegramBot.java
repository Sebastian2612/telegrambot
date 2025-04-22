package com.telegram.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;


@SpringBootApplication
public class TelegramBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBot.class, args);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new SimpleBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        // This method is correctly overridden from TelegramLongPollingBot
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText().toLowerCase();
            String replyText;

            if (userMessage.contains("ciao")) {
                replyText = "Ciao! Come posso aiutarti? 😊";
            } else if (userMessage.contains("come stai")) {
                replyText = "Sto benissimo, grazie! E tu? 🤖";
            } else if (userMessage.contains("grazie")) {
                replyText = "Di nulla! È un piacere aiutarti! 🙌";
            } else {
                replyText = "Hai scritto: " + update.getMessage().getText();
            }

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(replyText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "YourBotUsername"; // Replace with your bot's username
    }

    @Override
    public String getBotToken() {
        return "YourBotToken"; // Replace with your bot's token
    }
}
// END public class TelegramBot extends TelegramLongPollingBot
