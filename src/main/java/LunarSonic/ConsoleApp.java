package LunarSonic;
import LunarSonic.managers.CollectionManager;
import LunarSonic.managers.CommandManager;
import LunarSonic.managers.FileManager;
import LunarSonic.managers.Runner;
import LunarSonic.utility.AppLogger;
import LunarSonic.utility.Console;

/**
 * Класс, который запускает консольное приложение
 */
public class ConsoleApp {
    public static void main(String[] args) {
        var console = new Console();
        var logger = new AppLogger(ConsoleApp.class);
        var fileManager = new FileManager(console, logger);
        var collectionManager = new CollectionManager(fileManager);
        collectionManager.loadCollection();
        var commandManager = new CommandManager(console, collectionManager);
        new Runner(console, commandManager).interactiveMode();
    }
}
