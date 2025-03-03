package LunarSonic.utility;
import LunarSonic.managers.CommandManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Класс для исполнения программы
 */
public class Runner {
    private final Console console;
    private final CommandManager commandManager;
    private final AppLogger logger;
    private int lengthRecursion = -1;
    private final Deque<String> scriptStack = new ArrayDeque<>();

    /**
     * Конструктор класса Runner
     * @param console консоль
     * @param commandManager менеджер команд
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.logger = new AppLogger(Runner.class);
    }

    /**
     * Проверяет рекурсивность выполнения скрипта
     * @param argument название скрипта, который запускается
     * @param scriptScanner сканер для чтения из скрипта
     * @return true, если может быть рекурсия, иначе false
     */
    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var recStart = -1;
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (recStart < 0) recStart = i;
                if (lengthRecursion < 0) {
                    console.useConsoleScanner();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..300)");
                    while (lengthRecursion < 0 || lengthRecursion > 300) {
                        try {
                            console.print("> ");
                            lengthRecursion = Integer.parseInt(console.readInput().trim());
                        } catch (NumberFormatException e) {
                            logger.error("Длина не распознана");
                        }
                    }
                    console.useFileScanner(scriptScanner);
                }
                if (i > recStart + lengthRecursion || i > 300)
                    return false;
            }
        }
        return true;
    }

    /**
     * Метод для выполнения скрипта
     * @return результат выполнения скрипта
     */
    private ExecutionResponse executeScript(String fileName) {
        logger.info("Проверяем путь: " + fileName);
        Path path = Paths.get(fileName);
        if (!path.isAbsolute()) {
            path = Paths.get(System.getProperty("user.dir"), fileName);
        }
        logger.info("Абсолютный путь файла со скриптом: " + path.toAbsolutePath());
        File scriptFile = path.toFile();
        if (!scriptFile.exists()) {
            return new ExecutionResponse(false,"Файла нет: " + path.toAbsolutePath());
        }
        if (!scriptFile.canRead()) {
            return new ExecutionResponse(false, "Нет прав на чтение файла: " + path.toAbsolutePath());
        }
        scriptStack.addLast(fileName);
        logger.info("Файл найден, начинается выполнение скрипта ...");

        try (Scanner scannerForScript = new Scanner(scriptFile)) {
            ExecutionResponse statusOfCommand;
            if (!scannerForScript.hasNext()) throw new NoSuchElementException("Файл пустой!");
            //переключаем чтение из консоли на чтение из файла
            console.useFileScanner(scannerForScript);
            String[] userCommand;

            do {
                userCommand = (console.readInput().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                //выводим команду с приглашением
                console.println(console.getPrompt() + String.join(" ", userCommand));

                boolean isLaunchNeeded = true;
                if (userCommand[0].equals("execute_script")) {
                    isLaunchNeeded = checkRecursion(userCommand[1], scannerForScript);
                }
                //запускается команда
                statusOfCommand = isLaunchNeeded ? launchCommand(userCommand) : new ExecutionResponse(false, "Превышена max глубина рекурсии");
                if (userCommand[0].equals("execute_script")) console.useFileScanner(scannerForScript);
                console.println(statusOfCommand.getMessage());

                if (!statusOfCommand.getResponse()) {
                    console.println("Проверьте скрипт на корректность введенных данных!");
                }
            } while (console.hasNextInput() && statusOfCommand.getResponse() && !userCommand[0].equals("exit"));

            //снова чтение из консоли
            console.useConsoleScanner();
            return new ExecutionResponse("");

        } catch (FileNotFoundException e) {
            return new ExecutionResponse(false, "Файл не найден: " + path.toAbsolutePath());
        } catch (NoSuchElementException e) {
            return new ExecutionResponse(false, "Файл пустой: " + path.toAbsolutePath());
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка выполнения скрипта " + e.getMessage());
        } finally {
            scriptStack.pollLast();
        }
    }

    /**
     * Метод, который отвечает за запуск команд
     * @param userCommand массив строк, представляющих из себя команду и её аргументы
     * @return результат выполнения команды
     */
    private ExecutionResponse launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse("");
        var command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            return new ExecutionResponse(false, "Команда " + userCommand[0] + " не найдена");
        }
        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse response1 = commandManager.getCommands().get("execute_script").execute(userCommand);
            if (!response1.getResponse()) return response1;
            ExecutionResponse response2 = executeScript(userCommand[1]);
            return new ExecutionResponse(response2.getResponse(), response1.getMessage() + "\n" + response2.getMessage().trim());
        }
        return command.execute(userCommand);
    }

    /**
     * Метод для интерактивного режима работы программы,
     * в котором программа ожидает ввода команд от пользователя и выполняет их
     */
    public void interactiveMode() {
        try {
            ExecutionResponse statusOfCommand;
            var userCommand = new String[]{"", ""};
            while (true) {
                userCommand = (console.readInput().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (commandManager.getCommands().containsKey(userCommand[0])) {
                    commandManager.addCommandToHistory(userCommand[0]);
                }
                statusOfCommand = launchCommand(userCommand);
                if (userCommand[0].equals("exit"))
                    break;
                console.println(statusOfCommand.getMessage());
            }
        } catch (NoSuchElementException e) {
            logger.error("Пользовательский ввод не найден");
        } catch (IllegalStateException e) {
            logger.error("Непредвиденная ошибка");
        }
    }
}
