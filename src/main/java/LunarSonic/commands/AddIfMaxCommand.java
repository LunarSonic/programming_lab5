package LunarSonic.commands;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.objects.form.OrganizationForm;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды add_if_max
 */
public class AddIfMaxCommand extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса AddIfMaxCommand
     * @param collectionManager менеджер коллекции
     */
    public AddIfMaxCommand(CollectionManager collectionManager) {
        super(CommandName.add_if_max.name(), "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняется команда добавления нового элемента Organization в коллекцию, если его значение annualTurnover
     * больше max значения, которое есть в коллекции
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        try {
            if (!args[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
            OrganizationForm organizationForm = new OrganizationForm(collectionManager);
            Organization organization = organizationForm.form();
            boolean added = collectionManager.addIfMax(organization);
            if (!added) {
                return new ExecutionResponse(false, "Организация не была добавлена!");
            }
            return new ExecutionResponse("Максимальный элемент добавлен в коллекцию :)");
        } catch (FormBreak e) {
            return new ExecutionResponse(false, "Создание организации отменено");
        }
    }
}
