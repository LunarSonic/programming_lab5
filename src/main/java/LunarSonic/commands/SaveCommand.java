package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

public class SaveCommand extends Command {
    protected final CollectionManager collectionManager;
    protected final Console console;

    /**
     * Конструктор класса SaveCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public SaveCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.save.name(), "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.console = console;
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
        return new ExecutionResponse(true, "");
    }
}
