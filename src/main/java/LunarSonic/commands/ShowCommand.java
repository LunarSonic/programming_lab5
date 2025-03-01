package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды show
 */
public class ShowCommand extends Command {
    protected final CollectionManager collectionManager;
    protected final Console console;

    /**
     * Конструктор класса ShowCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public ShowCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.show.name(), "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняется команда вывода всех элементов коллекции в строковом виде
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        console.println(collectionManager);
        return new ExecutionResponse(true, "");
    }
}
