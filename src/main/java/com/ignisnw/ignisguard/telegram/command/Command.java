package com.ignisnw.ignisguard.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Command {
    void execute(Update update);
    String getName();
    List<String> getAliases();
    String getDescription(); // Add this method
    String getUsage(); // Add this method
}