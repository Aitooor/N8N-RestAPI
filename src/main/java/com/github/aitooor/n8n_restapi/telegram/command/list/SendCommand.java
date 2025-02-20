package com.github.aitooor.n8n_restapi.telegram.command.list;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aitooor.n8n_restapi.dto.IdeaYoutubeModelDTO;
import com.github.aitooor.n8n_restapi.model.IdeaYoutubeModel;
import com.github.aitooor.n8n_restapi.service.list.IdeaYoutubeService;
import com.github.aitooor.n8n_restapi.telegram.TelegramBot;
import com.github.aitooor.n8n_restapi.telegram.command.Command;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Getter
public class SendCommand implements Command {

    private final TelegramBot telegramBot;
    private final IdeaYoutubeService ideaYoutubeService;
    private final String name = "send";
    private final List<String> aliases = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<IdeaYoutubeModel> ideaYoutubeModels = new ArrayList<>();

    public SendCommand(TelegramBot telegramBot, IdeaYoutubeService ideaYoutubeService) {
        this.telegramBot = telegramBot;
        this.ideaYoutubeService = ideaYoutubeService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String messageText = update.getMessage().getText();
        String[] parts = messageText.split(" ", 3);

        if (parts.length < 2) {
            sendMessage(chatId, this.getUsage());
            return;
        }

        Set<String> documentUrls = new HashSet<>(Arrays.asList(parts[1].split(",")));
        Set<String> imageUrls = new HashSet<>();

        if (parts.length > 2) {
            for (String url : parts[2].split(",")) {
                if (url.matches(".*\\.(jpg|jpeg|png|gif|bmp|webp)$")) {
                    imageUrls.add(url);
                } else {
                    documentUrls.add(url);
                }
            }
        }

        StringBuilder responseText = new StringBuilder("Document URLs:\n");
        documentUrls.forEach(url -> responseText.append("- ").append(url).append("\n"));
        responseText.append("\nImage URLs:\n");
        imageUrls.forEach(url -> responseText.append("- ").append(url).append("\n"));

        sendMessage(chatId, responseText.toString());

        // Create a new model and parse it to JSON using Jackson
        IdeaYoutubeModel ideaYoutubeModel = new IdeaYoutubeModel(null, new ArrayList<>(documentUrls), new ArrayList<>(imageUrls));
        ideaYoutubeModels.add(ideaYoutubeModel);
        try {
            String json = objectMapper.writeValueAsString(ideaYoutubeModel);
            sendMessage(chatId, "Parsed JSON:\n" + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            sendMessage(chatId, "Failed to parse JSON");
        }

        ideaYoutubeService.create(IdeaYoutubeModelDTO.fromEntity(ideaYoutubeModel));
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Send a document with URLs.";
    }

    @Override
    public String getUsage() {
        return "Usage: /" + name +  " <documentUrls> <imageUrls>";
    }
}