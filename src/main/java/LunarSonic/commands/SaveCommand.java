package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды save
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса SaveCommand
     * @param collectionManager менеджер коллекции
     */
    public SaveCommand(CollectionManager collectionManager) {
        super(CommandName.save.name(), "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняется команда сохранения коллекции в файл
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        collectionManager.saveCollection();
        return new ExecutionResponse("Организация сохранена в CSV файл :)");
    }
}
