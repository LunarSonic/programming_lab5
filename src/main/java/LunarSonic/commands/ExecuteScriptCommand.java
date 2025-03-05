package LunarSonic.commands;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды execute_script
 */
public class ExecuteScriptCommand extends Command {

    /**
     * Конструктор класса ExecuteScriptCommand
     */
    public ExecuteScriptCommand() {
        super(CommandName.execute_script.name(), "считать и исполнить скрипт из указанного файла");
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
