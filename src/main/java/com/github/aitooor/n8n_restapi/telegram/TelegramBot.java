package com.github.aitooor.n8n_restapi.telegram;

import com.github.aitooor.n8n_restapi.config.BotConfigFile;
import com.github.aitooor.n8n_restapi.telegram.command.Command;
import com.github.aitooor.n8n_restapi.telegram.command.CommandManager;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private BotConfigFile botConfigFile;
    @Getter
    private final TelegramBotsApi botsApi;

    private final CommandManager commandManager;

    public TelegramBot(@Lazy CommandManager commandManager) {
        this.commandManager = commandManager;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return botConfigFile.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfigFile.getUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (messageText.equals("/help")) {
                sendHelpMessage(chatId);
            } else if (messageText.startsWith("/")) {
                String commandName = messageText.split(" ")[0].substring(1);
                Command command = commandManager.getCommand(commandName);

                if (command != null) {
                    command.execute(update);
                } else {
                    List<String> suggestions = commandManager.getCommandSuggestions(commandName);
                    if (!suggestions.isEmpty()) {
                        sendChatAction(chatId, ActionType.TYPING);
                        StringBuilder suggestionMessage = new StringBuilder("Did you mean:\n");
                        suggestions.forEach(suggestion -> suggestionMessage.append("/").append(suggestion).append("\n"));

                        sendMessage(chatId, suggestionMessage.toString());
                    }
                }
            }
        }
    }

    private void sendHelpMessage(String chatId) {
        StringBuilder helpMessage = new StringBuilder("Available commands:\n\n");
        for (Map.Entry<String, Command> entry : commandManager.getCommands().entrySet()) {
            helpMessage.append(entry.getKey()).append(" - ").append(entry.getValue().getDescription()).append("\n").append(entry.getValue().getUsage()).append("\n\n");
        }
        sendMessage(chatId, helpMessage.toString());
    }

    private void sendChatAction(String chatId, ActionType action) {
        SendChatAction chatAction = new SendChatAction();
        chatAction.setChatId(chatId);
        chatAction.setAction(action);
        try {
            execute(chatAction);
        } catch (TelegramApiException e) {
            log.error("Failed to send chat action", e);
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            botsApi.registerBot(this);
            log.info("Telegram bot registered successfully");
        } catch (TelegramApiException e) {
            log.error("Failed to register Telegram bot", e);
        }
    }
}