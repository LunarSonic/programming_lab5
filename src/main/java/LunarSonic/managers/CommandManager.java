package LunarSonic.managers;
import LunarSonic.commands.*;
import LunarSonic.commands.Command;
import LunarSonic.commands.CommandName;
import LunarSonic.utility.Console;
import java.util.*;

/**
 * Класс для управления командами
 */
public class CommandManager {
    private final Console console;
    private final CollectionManager collectionManager;
    private static final int COMMAND_HISTORY_SIZE = 15;
    protected Queue<String> commandHistory = new ArrayDeque<>();
    private final Map<String, Command> commands = new HashMap<>(); //Словарь, который необходим для хранения команд и их названий

    /**
     * Конструктор менеджера команд
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public CommandManager(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        initCommands();
    }

    /**
     * Метод для получения истории команд
     * @return история команд
     */
    public Queue<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Метод для регистрации команды
     * @param commandName название команды
     * @param command команда
     */
    public void registerCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Метод для инициализации команд в Command Manager
     */
    public void initCommands() {
        registerCommand(CommandName.add.name(), new AddCommand(collectionManager, console));
        registerCommand(CommandName.add_if_max.name(), new AddIfMaxCommand(collectionManager, console));
        registerCommand(CommandName.add_if_min.name(), new AddIfMinCommand(collectionManager, console));
        registerCommand(CommandName.clear.name(), new ClearCommand(collectionManager, console));
        registerCommand(CommandName.execute_script.name(), new ExecuteScriptCommand(console, this));
        registerCommand(CommandName.exit.name(), new ExitCommand(collectionManager, console));
        registerCommand(CommandName.help.name(), new HelpCommand(this, console));
        registerCommand(CommandName.history.name(), new HistoryCommand(this, console));
        registerCommand(CommandName.info.name(), new InfoCommand(collectionManager, console));
        registerCommand(CommandName.max_by_postal_address.name(), new MaxByPostalAddressCommand(collectionManager, console));
        registerCommand(CommandName.remove_all_by_annual_turnover.name(), new RemoveAllByAnnualTurnoverCommand(collectionManager, console));
        registerCommand(CommandName.remove_by_id.name(), new RemoveByIdCommand(collectionManager, console));
        registerCommand(CommandName.save.name(), new SaveCommand(collectionManager, console));
        registerCommand(CommandName.show.name(), new ShowCommand(collectionManager, console));
        registerCommand(CommandName.sum_of_annual_turnover.name(), new SumOfAnnualTurnoverCommand(collectionManager, console));
        registerCommand(CommandName.update.name(), new UpdateCommand(collectionManager, console));
    }

    /**
     * Метод для добавления команды в историю команд (история всегда обновляется, всего может быть максимум 15 команд)
     * @param command команда, которая добавляется в историю команд
     */
    public void addCommandToHistory(String command) {
        if (commandHistory.size() >= COMMAND_HISTORY_SIZE) {
                commandHistory.poll();
        }
        commandHistory.offer(command);
    }

    /**
     * Метод для получения всех команд, добавленных в Command Manager
     * @return команды, которые хранятся в Command Manager
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}

