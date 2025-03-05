package LunarSonic.commands;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.utility.ExecutionResponse;
import LunarSonic.objects.form.OrganizationForm;

/**
 * Класс команды add_if_min
 */
public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса AddIfMinCommand
     * @param collectionManager менеджер коллекции
     */
    public AddIfMinCommand(CollectionManager collectionManager) {
        super(CommandName.add_if_min.name(), "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняется команда добавления нового элемента Organization в коллекцию, если его значение annualTurnover
     * меньше min значения, которое есть в коллекции
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
            boolean added = collectionManager.addIfMin(organization);
            if (!added) {
                return new ExecutionResponse(false, "Организация не была добавлена!");
            }
            return new ExecutionResponse("Минимальный элемент добавлен в коллекцию :)");

        } catch (FormBreak e) {
            return new ExecutionResponse(false, "Создание организации отменено");
        }
    }
}
