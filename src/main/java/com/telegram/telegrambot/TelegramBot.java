package com.telegram.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // Verifica se il messaggio ha testo
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText().toLowerCase();
            String replyText;

            // Risposta personalizzata
            if (userMessage.contains("ciao")) {
                replyText = "Ciao! Come posso aiutarti? 😊";
            } else if (userMessage.contains("come stai")) {
                replyText = "Sto benissimo, grazie! E tu? 🤖";
            } else if (userMessage.contains("grazie")) {
                replyText = "Di nulla! È un piacere aiutarti! 🙌";
            } else {
                replyText = "Hai scritto: " + update.getMessage().getText();
            }

            // Crea il messaggio da inviare
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(replyText);

            try {
                execute(message); // Invia il messaggio
            } catch (TelegramApiException e) {
                e.printStackTrace(); // Se c'è un errore, lo stampiamo
            }
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME"); // Username del bot
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN"); // Token del bot
    }
}
