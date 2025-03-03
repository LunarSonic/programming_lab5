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
    private final Map<String, Command> commands; //Словарь, который необходим для хранения команд и их названий

    /**
     * Конструктор менеджера команд
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public CommandManager(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        this.commands = initCommands();
    }

    /**
     * Метод для получения истории команд
     * @return история команд
     */
    public Queue<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Метод для инициализации команд в Command Manager
     */
    public Map<String, Command> initCommands() {
        return Map.ofEntries(
                Map.entry(CommandName.add.name(), new AddCommand(collectionManager, console)),
                Map.entry(CommandName.add_if_max.name(), new AddIfMaxCommand(collectionManager, console)),
                Map.entry(CommandName.add_if_min.name(), new AddIfMinCommand(collectionManager, console)),
                Map.entry(CommandName.clear.name(), new ClearCommand(collectionManager, console)),
                Map.entry(CommandName.execute_script.name(), new ExecuteScriptCommand(console, this)),
                Map.entry(CommandName.exit.name(), new ExitCommand(collectionManager, console)),
                Map.entry(CommandName.help.name(), new HelpCommand(this, console)),
                Map.entry(CommandName.history.name(), new HistoryCommand(this, console)),
                Map.entry(CommandName.info.name(), new InfoCommand(collectionManager, console)),
                Map.entry(CommandName.max_by_postal_address.name(), new MaxByPostalAddressCommand(collectionManager, console)),
                Map.entry(CommandName.remove_all_by_annual_turnover.name(), new RemoveAllByAnnualTurnoverCommand(collectionManager, console)),
                Map.entry(CommandName.remove_by_id.name(), new RemoveByIdCommand(collectionManager, console)),
                Map.entry(CommandName.save.name(), new SaveCommand(collectionManager, console)),
                Map.entry(CommandName.show.name(), new ShowCommand(collectionManager, console)),
                Map.entry(CommandName.sum_of_annual_turnover.name(), new SumOfAnnualTurnoverCommand(collectionManager, console)),
                Map.entry(CommandName.update.name(), new UpdateCommand(collectionManager, console))
        );
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

