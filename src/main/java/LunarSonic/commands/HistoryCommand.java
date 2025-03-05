package LunarSonic.commands;
import LunarSonic.managers.CommandManager;
import LunarSonic.utility.ExecutionResponse;
import LunarSonic.utility.AppLogger;

/**
 * Класс команды history для вывода последних 15 команд
 */
public class HistoryCommand extends Command{
    private final CommandManager commandManager;
    private final AppLogger logger;

    /**
     * Конструктор класса HistoryCommand
     * @param commandManager менеджер команд
     */
    public HistoryCommand(CommandManager commandManager) {
        super(CommandName.history.name(), "вывести последние 15 команд (без их аргументов)");
        this.commandManager = commandManager;
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
