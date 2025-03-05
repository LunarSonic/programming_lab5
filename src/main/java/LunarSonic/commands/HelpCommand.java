package LunarSonic.commands;
import LunarSonic.managers.CommandManager;
import LunarSonic.utility.ExecutionResponse;

/**
 * Класс команды help
 */
public class HelpCommand extends Command {
    protected final CommandManager commandManager;

    /**
     * Конструктор класса HelpCommand
     * @param commandManager менеджер команд
     */
    public HelpCommand(CommandManager commandManager) {
        super(CommandName.help.name(),"вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Выполняется команда вывода справки по всем доступным командам
     * @param args массив с аргументами команды
     * @return успешность выполнения команды
     */
    @Override
    public ExecutionResponse execute(String[] args) {
        if (!args[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное кол-во аргументов!\n");
        StringBuilder helpMessage = new StringBuilder("Справка по доступным командам:\n");
        commandManager.getCommands().values().forEach(command -> helpMessage.append(
                String.format(" %-35s%-1s%n", command.getCommandName(), command.getDescription())));
        return new ExecutionResponse(helpMessage.toString());
    }
}
