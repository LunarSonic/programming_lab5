package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс команды info
 */
public class InfoCommand extends Command {
    protected final CollectionManager collectionManager;
    protected final Console console;

    /**
     * Конструктор класса InfoCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public InfoCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.info.name(), "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняется команда вывода информации о коллекции
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        ZonedDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в этой сессии ещё не было инициалиазции" : lastInitTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        ZonedDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в этой сессии ещё не было сохранения" : lastSaveTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        console.println("Информация о коллекции: " + "\n" + "Тип: " + collectionManager.getOrganizationCollection().getClass() + "\n" +
                "Кол-во элементов: " + collectionManager.getOrganizationCollection().size() + "\n" + "Дата последней инициалиазции: " + lastInitTimeString + "\n" +
                "Дата последнего сохранения: " + lastSaveTimeString);
        return new ExecutionResponse("");
    }
}
