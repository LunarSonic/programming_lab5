package LunarSonic.objects.form;
import LunarSonic.exceptions.FormBreak;

/**
 * Абстрактный класс формы для ввода пользовательских данных
 * @param <T>
 */
public abstract class BasicFormation<T> {
    public abstract T form() throws FormBreak;
}
