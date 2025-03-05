package LunarSonic.objects.form;
import LunarSonic.objects.Address;
import LunarSonic.utility.AppLogger;
import LunarSonic.utility.Console;
import java.util.NoSuchElementException;

/**
 * Класс для формирования адреса
 */
public class AddressForm extends BasicFormation<Address> {
    private final Console console;
    private final AppLogger logger;


    public AddressForm() {
        this.console = Console.getConsoleInstance();
        this.logger = new AppLogger(AddressForm.class);
    }

    @Override
    public Address form() {
        String street = askStreet();
        return new Address(street);
    }

    /**
     * Метод, который запрашивает улицу у пользователя
     */
    private String askStreet(){
        String street;
        while (true) {
            try {
                console.println("Введите название улицы: ");
                String line = console.readInput().trim();
                if (!line.isEmpty()){
                    street = line;
                    break;
                } else {
                    logger.error("Поле не может быть null");
                }
            } catch (IllegalStateException e) {
                logger.error("Непредвиденная ошибка");
                System.exit(0);
            } catch (NoSuchElementException e) {
                logger.error("Данное значение поля не может быть использовано");
            }
        }
        return street;
    }
}

