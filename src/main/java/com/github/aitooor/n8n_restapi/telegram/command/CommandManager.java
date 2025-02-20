package com.github.aitooor.n8n_restapi.telegram.command;

import com.github.aitooor.n8n_restapi.service.list.IdeaYoutubeService;
import com.github.aitooor.n8n_restapi.telegram.TelegramBot;
import com.github.aitooor.n8n_restapi.telegram.command.list.SendCommand;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandManager {
    @Getter
    private final Map<String, Command> commands = new HashMap<>();

    private final TelegramBot telegramBot;
    private final IdeaYoutubeService ideaYoutubeService;

    public CommandManager(@Lazy TelegramBot telegramBot, IdeaYoutubeService ideaYoutubeService) {
        this.telegramBot = telegramBot;
        this.ideaYoutubeService = ideaYoutubeService;
    }

    @PostConstruct
    private void initializeCommands() {
        SendCommand sendCommand = new SendCommand(telegramBot, ideaYoutubeService);
        registerCommand(sendCommand);
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
        for (String alias : command.getAliases()) {
            commands.put(alias, command);
        }
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    public List<String> getCommandSuggestions(String prefix) {
        return commands.keySet().stream()
                .filter(name -> name.startsWith(prefix))
                .collect(Collectors.toList());
    }
}