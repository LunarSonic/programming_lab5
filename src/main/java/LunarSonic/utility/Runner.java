package LunarSonic.utility;
import LunarSonic.managers.CommandManager;
import LunarSonic.managers.ScriptManager;
import java.util.*;

/**
 * Класс для исполнения программы
 */
public class Runner {
    private final Console console;
    private final CommandManager commandManager;
    private final AppLogger logger;
    private final ScriptManager scriptManager;

    /**
     * Конструктор класса Runner
     * @param console консоль
     * @param commandManager менеджер команд
     */
    public Runner(Console console, CommandManager commandManager, ScriptManager scriptManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.scriptManager = scriptManager;
        this.logger = new AppLogger(Runner.class);
    }

    /**
     * Метод, который отвечает за запуск команд
     * @param userCommand массив строк, представляющих из себя команду и её аргументы
     * @return результат выполнения команды
     */
    public ExecutionResponse launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse("");
        var command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            return new ExecutionResponse(false, "Команда " + userCommand[0] + " не найдена");
        }
        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse response1 = commandManager.getCommands().get("execute_script").execute(userCommand);
            if (!response1.getResponse()) return response1;
            ExecutionResponse response2 = scriptManager.executeScript(userCommand[1], this);
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
