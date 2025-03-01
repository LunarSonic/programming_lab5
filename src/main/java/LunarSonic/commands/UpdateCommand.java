package LunarSonic.commands;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Organization;
import LunarSonic.objects.form.OrganizationForm;
import LunarSonic.utility.Console;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды update
 */
public class UpdateCommand extends Command {
    protected final CollectionManager collectionManager;
    protected final Console console;

    /**
     * Конструктор класса UpdateCommand
     * @param collectionManager менеджер коллекции
     * @param console консоль
     */
    public UpdateCommand(CollectionManager collectionManager, Console console) {
        super(CommandName.update.name(), "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняется команда обновления значения элемента коллекции, у которого id равен заданному
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        try {
            if (args[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
            long id;
            try {
                id = Long.parseLong(args[1].trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "id не был распознан!");
            }

            var previous_id = collectionManager.getObjectById(id);
            if (!collectionManager.getOrganizationCollection().contains(previous_id) || previous_id == null) {
                return new ExecutionResponse(false, "Такого id не существует!");
            }

            console.println("Обновляется организация с id " + id);
            OrganizationForm organizationForm = new OrganizationForm(collectionManager, console);
            Organization newOrganization = organizationForm.form();
            if (newOrganization != null) {
                collectionManager.replaceOrganizationById(id, newOrganization);
                collectionManager.sort();
                return new ExecutionResponse("Элемент обновлён :)");
            } else {
                return new ExecutionResponse(false, "Поля у организации не валидны!");
            }
        } catch (FormBreak e) {
            return new ExecutionResponse(false, "Создание организации отменено");
        }
    }
}
