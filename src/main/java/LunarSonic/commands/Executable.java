package LunarSonic.commands;
import LunarSonic.utility.ExecutionResponse;

/**
 * Интерфейс для выполнения команд
 */
public interface Executable {
    ExecutionResponse execute(String[] args);
}
