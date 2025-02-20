package com.github.aitooor.n8n_restapi.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Command {
    void execute(Update update);
    String getName();
    List<String> getAliases();
    String getDescription(); // Add this method
    String getUsage(); // Add this method
}