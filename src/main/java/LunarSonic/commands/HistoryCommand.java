package LunarSonic.commands;
import LunarSonic.managers.CommandManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;
import LunarSonic.utility.AppLogger;

/**
 * Класс команды history для вывода последних 15 команд
 */
public class HistoryCommand extends Command{
    protected final CommandManager commandManager;
    protected final Console console;
    protected final AppLogger logger;

    /**
     * Конструктор класса HistoryCommand
     * @param commandManager менеджер команд
     * @param console консоль
     */
    public HistoryCommand(CommandManager commandManager, Console console) {
        super(CommandName.history.name(), "вывести последние 15 команд (без их аргументов)");
        this.commandManager = commandManager;
        this.console = console;
        this.logger = new AppLogger(HistoryCommand.class);
    }

    /**
     * Выполняется команда вывода истории команд
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        if (commandManager.getCommandHistory() == null) {
            logger.error("История пустая!");
        }
        StringBuilder historyOutput = new StringBuilder("Введённые команды:\n");
        for (String command : commandManager.getCommandHistory()) {
            historyOutput.append(command).append("\n");
        }
        return new ExecutionResponse(historyOutput.toString());
    }
}
