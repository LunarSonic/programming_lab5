package LunarSonic.objects.form;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.exceptions.NotInLimitsException;
import LunarSonic.managers.CollectionManager;
import LunarSonic.objects.Address;
import LunarSonic.objects.Coordinates;
import LunarSonic.objects.Organization;
import LunarSonic.objects.OrganizationType;
import LunarSonic.utility.AppLogger;
import LunarSonic.utility.Console;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

/**
 * Класс для формирования организации
 */
public class OrganizationForm extends BasicFormation<Organization> {
    private final CollectionManager collectionManager;
    private final Console console;
    private final AppLogger logger;

    public OrganizationForm(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.logger = new AppLogger(OrganizationForm.class);
    }

    @Override
    public Organization form() throws FormBreak {
        var organization = new Organization(setId(),
                askName(console), askCoordinates(console),
                ZonedDateTime.now(), askAnnualTurnover(console),
                askOrganizationType(console), askAddress(console)
        );
        if(!organization.validate()) throw new FormBreak();
        return organization;
    }

    public Long setId() {
        return collectionManager.generateNewId();
    }

    /**
     * Метод, который запращивает у пользователя название организации
     * @return name
     */
    private String askName(Console console) throws FormBreak {
        String name;
        while (true) {
            console.println("Введите название организации: ");
            try {
                name = console.readInput().trim();
                if (name.equals("exit")) throw new FormBreak();

                if (name.isEmpty()) {
                    logger.error("Поле не может быть пустым!");
                } else if (!name.matches("^[a-zA-Zа-яА-Я]+(?:'?[a-zA-Zа-яА-Я]+)*(?:\\s[a-zA-Zа-яА-Я]+(?:[a-zA-Zа-яА-Я]+)*)*$")) {
                    logger.error("Название организации может содержать только буквы, пробелы и 1 кавычку!");
                } else {
                    break;
                }
            } catch (IllegalStateException e) {
                logger.error("Непредвиденная ошибка");
                System.exit(0);
            } catch (NoSuchElementException e) {
                logger.error("Данное значение поля не может быть использовано");
            }
        }
        return name;
    }

    /**
     * Метод, который запрашивает координаты x и y
     * @param console консоль
     * @return coordinates
     */
    private Coordinates askCoordinates(Console console) throws FormBreak {
        return new CoordinatesForm(console, logger).form();
    }

    /**
     * Метод, который запрашивает годовой оборот организации
     * @param console консоль
     * @return annualTurnover
     */
    private long askAnnualTurnover(Console console) throws FormBreak {
        long annualTurnover;
        while (true) {
            try {
                console.println("Введите годовой оборот: ");
                String line = console.readInput().trim();
                if (line.equals("exit")) throw new FormBreak();
                if(!line.isEmpty()) {
                    annualTurnover = Long.parseLong(line);
                    if(annualTurnover <= 0) throw new NotInLimitsException();
                    break;
                }
            } catch (NotInLimitsException e) {
                logger.error("Значение поля должно быть больше 0");
            } catch (NumberFormatException e) {
                logger.error("Значение поля должно быть типа long");
            } catch (NoSuchElementException e) {
                logger.error("Значение поля не может быть использовано");
            } catch (IllegalStateException e) {
                logger.error("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return annualTurnover;
    }

    /**
     * Метод, который запрашивает тип организации
     * @param console консоль
     * @return organizationType
     */
    private OrganizationType askOrganizationType(Console console) throws FormBreak {
        return new OrganizationTypeForm(console, logger).form();
    }

    /**
     * Метод, который запрашивает адрес
     * @param console консоль
     * @return address
     */
    private Address askAddress(Console console){
        return new AddressForm(console, logger).form();
    }

    /**
     * Геттер для collectionManager
     * @return collectionManager
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
