package LunarSonic.commands;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.objects.form.OrganizationForm;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды add
 */
public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструктор класса AddCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public AddCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.add.name(), "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняется команда добавления нового элемента Organization в коллекцию
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        try {
            if (!args[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
            console.println("Создаётся новая организация");
            OrganizationForm organizationForm = new OrganizationForm(collectionManager, console);
            Organization organization = organizationForm.form();
            if (organization.validate()) {
                collectionManager.addElement(organization);
                return new ExecutionResponse("Организация успешно добавлена :)");
            } else {
                return new ExecutionResponse(false, "Поля у организации не валидны!");
            }
        } catch (FormBreak e) {
            return new ExecutionResponse(false, "Создание организации отменено");
        }
    }
}
