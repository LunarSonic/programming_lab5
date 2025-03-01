package LunarSonic.commands;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;
import LunarSonic.managers.CollectionManager;

/**
 * Класс команды clear
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструктор класса ClearCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public ClearCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.clear.name(), "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняется команда очистки коллекции
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        collectionManager.clearCollection();
        console.println("Коллекция была очищена");
        return new ExecutionResponse(true, "");
    }
}
