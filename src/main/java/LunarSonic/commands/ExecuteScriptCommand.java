package LunarSonic.commands;
import LunarSonic.managers.CommandManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды execute_script
 */
public class ExecuteScriptCommand extends Command{
    protected final CommandManager commandManager;
    protected final Console console;

    /**
     * Конструктор класса ExecuteScriptCommand
     * @param console консоль
     * @param commandManager менеджер команд
     */
    public ExecuteScriptCommand(Console console, CommandManager commandManager) {
        super(CommandName.execute_script.name(), "считать и исполнить скрипт из указанного файла");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняется команда выполнения скрипта
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        return new ExecutionResponse("");
    }
}
