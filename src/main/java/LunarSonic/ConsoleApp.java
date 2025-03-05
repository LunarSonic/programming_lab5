package LunarSonic;
import LunarSonic.managers.CollectionManager;
import LunarSonic.managers.CommandManager;
import LunarSonic.managers.FileManager;
import LunarSonic.utility.Runner;

/**
 * Класс, который запускает консольное приложение
 */
public class ConsoleApp {
    public static void main(String[] args) {
        var fileManager = new FileManager();
        var collectionManager = new CollectionManager(fileManager);
        collectionManager.loadCollection();
        var commandManager = new CommandManager(collectionManager);
        new Runner(commandManager).interactiveMode();
    }
}
