package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды exit
 */
public class ExitCommand extends Command {
    protected final CollectionManager collectionManager;
    protected final Console console;

    /**
     * Конструктор класса ExitCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public ExitCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.exit.name(), "завершить программу (без сохранения в файл)");
        this.collectionManager = collectionManager;
        this.console = console;
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
        return new ExecutionResponse("exit");
    }
}
