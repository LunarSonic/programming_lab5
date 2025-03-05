package LunarSonic.managers;
import LunarSonic.commands.*;
import LunarSonic.commands.Command;
import LunarSonic.commands.CommandName;
import java.util.*;

/**
 * Класс для управления командами
 */
public class CommandManager {
    private final CollectionManager collectionManager;
    private static final int COMMAND_HISTORY_SIZE = 15;
    protected Queue<String> commandHistory = new ArrayDeque<>();
    private final Map<String, Command> commands; //Словарь, который необходим для хранения команд и их названий

    /**
     * Конструктор менеджера команд
     * @param collectionManager менеджер коллекции
     */
    public CommandManager(CollectionManager collectionManager) {
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
     * Метод для инициализации команд
     * @return Map с командами
     */
    public Map<String, Command> initCommands() {
        return Map.ofEntries(
                Map.entry(CommandName.add.name(), new AddCommand(collectionManager)),
                Map.entry(CommandName.add_if_max.name(), new AddIfMaxCommand(collectionManager)),
                Map.entry(CommandName.add_if_min.name(), new AddIfMinCommand(collectionManager)),
                Map.entry(CommandName.clear.name(), new ClearCommand(collectionManager)),
                Map.entry(CommandName.execute_script.name(), new ExecuteScriptCommand()),
                Map.entry(CommandName.exit.name(), new ExitCommand()),
                Map.entry(CommandName.help.name(), new HelpCommand(this)),
                Map.entry(CommandName.history.name(), new HistoryCommand(this)),
                Map.entry(CommandName.info.name(), new InfoCommand(collectionManager)),
                Map.entry(CommandName.max_by_postal_address.name(), new MaxByPostalAddressCommand(collectionManager)),
                Map.entry(CommandName.remove_all_by_annual_turnover.name(), new RemoveAllByAnnualTurnoverCommand(collectionManager)),
                Map.entry(CommandName.remove_by_id.name(), new RemoveByIdCommand(collectionManager)),
                Map.entry(CommandName.save.name(), new SaveCommand(collectionManager)),
                Map.entry(CommandName.show.name(), new ShowCommand(collectionManager)),
                Map.entry(CommandName.sum_of_annual_turnover.name(), new SumOfAnnualTurnoverCommand(collectionManager)),
                Map.entry(CommandName.update.name(), new UpdateCommand(collectionManager))
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

