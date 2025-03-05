package LunarSonic.commands;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды exit
 */
public class ExitCommand extends Command {

    /**
     * Конструктор класса ExitCommand
     */
    public ExitCommand() {
        super(CommandName.exit.name(), "завершить программу (без сохранения в файл)");
    }

    /**
     * Выполняется команда завершения программы
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        return new ExecutionResponse("");
    }
}
