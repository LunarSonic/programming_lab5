package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды remove_all_by_annual_turnover
 */
public class RemoveAllByAnnualTurnoverCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструктор класса RemoveAllByAnnualTurnover
     * @param collectionManager менеджер коллекции
     */
    public RemoveAllByAnnualTurnoverCommand(CollectionManager collectionManager) {
        super(CommandName.remove_all_by_annual_turnover.name(), "удалить из коллекции все элементы, значение поля annualTurnover которого эквивалентно заданному");
        this.collectionManager = collectionManager;
        this.console = Console.getConsoleInstance();
    }

    /**
     * Выполняется команда удаления всех элементов из коллекции, у которых annualTurnover равен заданному
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        long annualTurnover;
        try {
            annualTurnover = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "annualTurnover не был распознан");
        }
        collectionManager.removeAllByAnnualTurnover(annualTurnover);
        console.println("Элементы коллекции с annualTurnover " + annualTurnover + " были удалены");
        return new ExecutionResponse("");
    }
}

