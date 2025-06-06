package LunarSonic.commands;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды max_by_postal_address
 */
public class MaxByPostalAddressCommand extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса MaxByPostalAddressCommand
     * @param collectionManager менеджер коллекции
     */
    public MaxByPostalAddressCommand(CollectionManager collectionManager) {
        super(CommandName.max_by_postal_address.name(), "вывести любой объект из коллекции, значение поля postalAddress которого является максимальным");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняется команда вывода объекта из коллекции, у которого значение postalAddress является max
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\n");
        }
        Organization my_max = null;
        for (Organization organization : collectionManager.getOrganizationCollection()) {
            if (organization.getPostalAddress().getStreet() != null) {
                if (my_max == null || organization.getPostalAddress().getStreet().compareTo(my_max.getPostalAddress().getStreet()) > 0) {
                    my_max = organization;
                }
            }
        }
        if (my_max != null) {
            return new ExecutionResponse(my_max.toString());
        } else {
            return new ExecutionResponse(false, "Организации не были найдены");
        }
    }
}
