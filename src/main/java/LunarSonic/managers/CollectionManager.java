package LunarSonic.managers;
import LunarSonic.objects.Organization;
import LunarSonic.utility.AppLogger;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс для управления коллекции
 */
public class CollectionManager {
    private final AppLogger logger; //Логгер для вывода дополнительной информации
    private LinkedHashSet<Organization> organizationCollection = new LinkedHashSet<>(); //Коллекция, которая хранится в менеджере
    private ZonedDateTime lastInitTime; //Время последней инициализации менеджера
    private ZonedDateTime lastSaveTime; //Время последнего сохранения коллекции
    private final FileManager fileManager;

    /**
     * Конструктор класса CollectionManager
     * @param fileManager менеджер файла
     */
    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.logger = new AppLogger(CollectionManager.class);
    }

    /**
     * Геттер для получения коллекции, которая хранится в менеджере
     * @return organizationCollection
     */
    public LinkedHashSet<Organization> getOrganizationCollection() {
        return this.organizationCollection;
    }

    /**
     * Геттер для получения времени последней инициализации менеджера
     * @return lastInitTime
     */
    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Геттер для получения времени последнего сохранения коллекции
     * @return lastSaveTime
     */
    public ZonedDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Метод для генерации нового id
     * Находим max id и прибавляем 1 к нему
     */
    public Long generateNewId() {
        long id = organizationCollection.stream()
                .mapToLong(Organization::getId)
                .max().orElse(0L);
        return id + 1;
    }

    /**
     * Метод, который сохраняет коллекцию в CSV файл
     */
    public void saveCollection() {
        fileManager.writeCollectionToCSV(getOrganizationCollection());
        lastSaveTime = ZonedDateTime.now();
    }

    /**
     * Метод, который позволяет получить объект коллекции по id
     * @param id уникальный id
     * @return объект Organization
     */
    public Organization getObjectById(Long id) {
        for (Organization organization : organizationCollection) {
            if (organization.getId().equals(id)) {
                return organization;
            }
        }
        return null;
    }

    /**
     * Метод, который добавляет объект в коллекцию, если
     * его значение превышает значение наибольшего элемента коллекции
     * @param organization коллекция
     */
    public boolean addIfMax(Organization organization) {
        if (organizationCollection.isEmpty()) {
            organizationCollection.add(organization);
            return true;
        }
        Organization maxElement = Collections.max(organizationCollection);
        logger.info("Максимальный элемент в коллекции: " + maxElement.getAnnualTurnover());
        logger.info("Происходит сравнение с " + organization.getAnnualTurnover());

        if (organization.compareTo(maxElement) > 0) {
            organizationCollection.add(organization);
            return true;
        }
        return false;
    }


    /**
     * Метод, который добавляет объект в коллекцию, если
     * его значение меньше значения чем у наименьшего элемента коллекции
     * @param organization коллекция
     */
    public boolean addIfMin(Organization organization) {
        if (organizationCollection.isEmpty()) {
            organizationCollection.add(organization);
            return true;
        }
        Organization minElement = Collections.min(organizationCollection);
        logger.info("Минимальный элемент в коллекции: " + minElement.getAnnualTurnover());
        logger.info("Происходит сравнение с " + organization.getAnnualTurnover());

        if (organization.compareTo(minElement) < 0) {
            organizationCollection.add(organization);
            return true;
        }
        return false;
    }

    /**
     * Метод, который загружает коллекцию из CSV файла
     */
    public void loadCollection() {
        organizationCollection.clear();
        fileManager.readCollectionFromCSV(organizationCollection);
        lastInitTime = ZonedDateTime.now();
        logger.info("Количество элементов в коллекции после загрузки: " + organizationCollection.size());
        sort();
    }

    /**
     * Метод, который добавляет элемент в коллекцию
     * @param organization коллекция
     */
    public void addElement(Organization organization) {
        organization.setId(generateNewId());
        organizationCollection.add(organization);
    }

    /**
     * Метод, который удаляет элемент коллекции по id
     * @param id организации
     */
    public void removeByIdFromCollection(Long id) {
        organizationCollection.stream()
                .filter(organization -> Objects.equals(organization.getId(), id))
                .findFirst()
                .ifPresent(organizationCollection::remove);
    }

    /**
     * Метод, который удаляет все элементы коллекций, у которых annualTurnover равен заданному
     * @param annualTurnover годовой оборот организации
     */
    public void removeAllByAnnualTurnover(long annualTurnover) {
        long firstSize = organizationCollection.size();
        organizationCollection = organizationCollection.stream()
                .filter(organization -> organization.getAnnualTurnover() != annualTurnover)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (organizationCollection.size() < firstSize)
            sort();
    }

    /**
     * Метод, который заменяет организацию с заданным id на новую организацию
     * @param id оргниазации, котороую следует обновить
     * @param newValue новая организация
     */
    public void replaceOrganizationById(Long id, Organization newValue) {
        newValue.setId(id);
        if (organizationCollection.removeIf(organization -> Objects.equals(organization.getId(), id))) {
            organizationCollection.add(newValue);
        }
    }

    /**
     * Метод, который очищает коллекцию
     */
    public void clearCollection() {
        organizationCollection.clear();
    }

    /**
     * Сортировка коллекции по id
     */
    public void sort() {
        organizationCollection = organizationCollection.stream()
                .sorted(Comparator.comparing(Organization::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Переопределённый метод toString()
     * @return строковое представление коллекции Organization
     */
    @Override
    public String toString() {
        if (organizationCollection.isEmpty()) {
            return "Коллекция пустая!";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization organization : organizationCollection) {
            stringBuilder.append(organization).append("\n");
        }
        return stringBuilder.toString();
    }
}
