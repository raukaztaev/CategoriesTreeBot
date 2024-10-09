package com.categoriestreebot.controller;

import com.categoriestreebot.command.Command;
import com.categoriestreebot.handler.CommandHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Class representing the Telegram bot for managing category commands.
 * <p>
 * Bot listens for incoming messages and delegates command execution
 * to the appropriate command handler
 */
@Component
@Slf4j
public class CategoryBot extends TelegramLongPollingBot {
    private final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    private final CommandHandler commandHandler;

    @Value("${telegram.bot.username}")
    private String botUserName;
    @Value("${telegram.bot.token}")
    private String botToken;

    public CategoryBot(CommandHandler commandHandler) throws TelegramApiException {
        this.commandHandler = commandHandler;
    }

    /**
     * Registers the bot with the Telegram Bots API after construction.
     * This method is called after the bean has been created.
     *
     * @throws TelegramApiException if an error occurs during bot registration
     */
    @PostConstruct
    private void init() throws TelegramApiException {
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Handles incoming updates from Telegram.
     * <p>
     * If the update contains a message with text, it retrieves the command
     * associated with that message and executes it. If the command is invalid,
     * an error message is sent back to the user.
     * </p>
     *
     * @param update the incoming update containing the message
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Command command = commandHandler.getCommand(message.split("\\s")[0]);

            if (command == null) {
                sendMessage(update.getMessage().getChatId(),
                        "Invalid command. Type /help to see all available commands");
            } else {
                command.execute(update, this);
            }
        }
    }

    /**
     * Sends a message to a specified chat.
     *
     * @param chatId  the ID of the chat to send the message to
     * @param message the message text to be sent
     */
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Problems with connection to Telegram API. Details: {}", e);
        }

    }
}
