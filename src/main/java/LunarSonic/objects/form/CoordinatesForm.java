package LunarSonic.objects.form;
import LunarSonic.exceptions.FormBreak;
import LunarSonic.exceptions.NotInLimitsException;
import LunarSonic.objects.Coordinates;
import LunarSonic.utility.AppLogger;
import LunarSonic.utility.Console;
import java.util.NoSuchElementException;

/**
 * Класс для формирования координат
 */
public class CoordinatesForm extends BasicFormation<Coordinates> {
    private final Console console;
    private final AppLogger logger;

    public CoordinatesForm(Console console, AppLogger logger) {
        this.console = console;
        this.logger = logger;
    }

    @Override
    public Coordinates form() throws FormBreak {
        Float x = askX(console, logger);
        Long y = askY(console, logger);
        return new Coordinates(x, y);
    }

    /**
     * Метод, который запрашивает координату X
     * @param console консоль для ввода
     * @return значение координаты X
     */
    private static Float askX(Console console, AppLogger logger) throws FormBreak {
        float x;
        while (true) {
            try {
                console.println("Введите координату X (тип Float): ");
                String line = console.readInput().trim();
                if (line.equals("exit")) throw new FormBreak();
                if (!line.isEmpty()) {
                    x = Float.parseFloat(line);
                    if (x <= -947) throw new NotInLimitsException();
                    break;
                } else {
                    logger.error("Поле не может быть null");
                }
            } catch (NotInLimitsException e) {
                logger.error("Значение должно быть больше -947");
            } catch (NumberFormatException e) {
                logger.error("Значение должно быть типа Float");
            } catch (NoSuchElementException e) {
                logger.error("Данное значение поля не может быть использовано");
            } catch (IllegalStateException e) {
                logger.error("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Метод, который запрашивает координату Y
     * @param console консоль для ввода
     * @return значение координаты Y
     */
    private static Long askY(Console console, AppLogger logger) throws FormBreak {
        long y;
        while (true) {
            try {
                console.println("Введите координату Y (тип Long): ");
                String line = console.readInput().trim();
                if (line.equals("exit")) throw new FormBreak();
                if (!line.isEmpty()) {
                    y = Long.parseLong(line);
                    break;
                } else {
                    logger.error("Поле не может быть null");
                }
            } catch (NumberFormatException e) {
                logger.error("Значение должно быть типа Long");
            } catch (IllegalStateException e) {
                logger.error("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return y;
    }
}
