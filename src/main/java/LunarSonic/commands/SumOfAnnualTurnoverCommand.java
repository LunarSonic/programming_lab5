package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды sum_of_annualTurnover
 */
public class SumOfAnnualTurnoverCommand extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса SumOfAnnualTurnoverCommand
     * @param collectionManager менеджер коллекции
     */
    public SumOfAnnualTurnoverCommand(CollectionManager collectionManager) {
        super(CommandName.sum_of_annual_turnover.name(), "вывести сумму значений поля annualTurnover для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняется команда вывода суммы всех значений annualTurnover
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        long sum = 0;
        for (Organization org : collectionManager.getOrganizationCollection()) {
            sum += org.getAnnualTurnover();
        }
        return new ExecutionResponse("Сумма годового оборота у всех оранизаций: " + sum);
    }
}
