package LunarSonic.objects.form;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.objects.OrganizationType;
import LunarSonic.utility.AppLogger;
import LunarSonic.utility.Console;
import java.util.NoSuchElementException;

/**
 * Класс для формирования типа организации
 */
public class OrganizationTypeForm extends BasicFormation<OrganizationType> {
    private final Console console;
    private final AppLogger logger;

    public OrganizationTypeForm() {
        this.console = Console.getConsoleInstance();
        this.logger = new AppLogger(OrganizationTypeForm.class);
    }

    @Override
    public OrganizationType form() throws FormBreak {
        while (true) {
            try {
                console.println("Типы оргнизаций: " + OrganizationType.organizationNameList());
                console.println("Введите тип организации: ");
                String line = console.readInput().trim();
                if (line.equals("exit")) throw new FormBreak();
                if (line.isEmpty()) return null;
                return OrganizationType.valueOf(line);
            } catch (IllegalArgumentException e) {
                logger.error("Такого типа организации нет в списке");
            } catch (IllegalStateException e) {
                logger.error("Непредвиденная ошибка");
                System.exit(0);
            } catch (NoSuchElementException e) {
                logger.error("Данное значение поля не может быть использовано");
            }
            console.println("");
            console.print("Введите тип организации: ");
        }
    }
}
