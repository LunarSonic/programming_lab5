package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды remove_by_id
 */
public class RemoveByIdCommand extends Command {
    protected final CollectionManager collectionManager;
    protected final Console console;

    /**
     * Конструктор класса RemoveByIdCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public RemoveByIdCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.remove_by_id.name(), "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняется команда удаления элемента Organization из коллекции по id
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        long id;
        try {
            id = Long.parseLong(args[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "id не был распознан");
        }
        Organization organization = collectionManager.getObjectById(id);
        if (organization == null) {
            return new ExecutionResponse(false, "Такого id не существует");
        }
        collectionManager.removeByIdFromCollection(id);
        console.println("Элемент коллекции c id: " + id + " был удалён");
        return new ExecutionResponse("");
    }
}
