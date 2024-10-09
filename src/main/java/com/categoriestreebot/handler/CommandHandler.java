package com.categoriestreebot.handler;

import com.categoriestreebot.command.Command;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command handler for managing bot commands.
 * <p>
 * This class is responsible for storing and retrieving command instances
 * based on the command name.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final List<Command> commandBeans;

    @PostConstruct
    private void init() {
        commandBeans.forEach(command -> commands.put(command.getCommandName(), command));
    }

    /**
     * Retrieves a Command instance by its name.
     *
     * @param commandName the name of the command to retrieve
     * @return the Command instance if found, or null if not found
     */
    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
