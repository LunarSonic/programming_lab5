package LunarSonic.utility;

/**
 * Абстрактный класс модели
 */
public abstract class Model implements Validatable, Comparable<Model> {

    /**
     * Абстрактный метод для получения годового оборота компании
     * @return annualTurnover
     */
    public abstract long getAnnualTurnover();
}
