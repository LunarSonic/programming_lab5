package LunarSonic.utility;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для ввода команд и вывода результата
 */
public class Console {

    protected PrintStream console;

    /**
     * Строка приглашения
     */
    private static final String prompt = "$ ";

    /**
     * Сканер для чтения из файла
     */
    private static Scanner fileScanner = null;

    /**
     * Сканер для чтения из стандартного ввода
     */
    private static final Scanner defScanner = new Scanner(System.in);

    /**
     * Конструктор класса Console
     */
    public Console() {
        this.console = System.out;
    }

    /**
     * Сеттер для получения объекта Console
     * @param console консоль
     */
    public void setConsole(PrintStream console) {
        this.console = console;
    }

    /**
     * Выводит obj.toString() в консоль
     * @param obj объект, который будет выведен
     */
    public void print(Object obj) {
        console.print(obj);
    }

    /**
     * Выводит obj.toString() + \n (перенос строки) в консоль
     * @param obj объект, который будет выведен
     */
    public void println(Object obj) {
        console.println(obj);
    }

    /**
     * Чтение строки из fileScanner, если он активен,
     * если не активен, то чтение строки из defScanner
     * @return возвращает следующую строку
     */
    public String readInput() throws IllegalStateException, NoSuchElementException {
        if (fileScanner != null) {
            //чтение строки из файла
            return fileScanner.nextLine();
        } else {
            //чтение строки из консоли
            return defScanner.nextLine();
        }
    }

    /**
     * Проверка, есть ли ещё строки для чтения
     * @return возвращает true, если ещё остались строки для чтения, иначе false
     */
    public boolean hasNextInput() {
        if (fileScanner != null) {
            return fileScanner.hasNextLine();
        } else {
            return defScanner.hasNextLine();
        }
    }

    /**
     * Геттер для получения текущего prompt (приглашения)
     * @return prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Переключает ввод на файл, ввод данных будет из переданного сканера
     * @param scanner сканер для чтения из файла
     */
    public void useFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    /**
     * Переключает ввод на стандартный поток вывода (консоль)
     */
    public void useConsoleScanner() {
        fileScanner = null;
    }
}

