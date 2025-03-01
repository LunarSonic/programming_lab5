package LunarSonic.managers;
import LunarSonic.objects.Organization;
import LunarSonic.utility.AppLogger;
import LunarSonic.utility.OrgCSVParser;
import LunarSonic.utility.Console;
import com.opencsv.CSVReader;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, отвечающий за работу с файлами
 */
public class FileManager {
    private final Set<String> allFilePaths;
    private final String csvFilePath;
    private final AppLogger logger;
    private final Console console;

    /**
     * Конструктор класса FileManager
     * @param logger для вывода сообщений
     */
    public FileManager(Console console, AppLogger logger) {
        this.console = console;
        this.logger = logger;
        this.allFilePaths = loadFilesFromEnvironmentVariables();
        this.csvFilePath = findOrgCSVFilePath();
    }

    /**
     * Метод, который возвращает список файлов, полученных из переменной окружения LAB5_PATH
     * @return filePaths список из всех путей
     */
    public Set<String> loadFilesFromEnvironmentVariables() {
        String lab5Path = System.getenv("LAB5_PATH");
        if (lab5Path == null) {
            logger.error("Переменная окружения LAB5_PATH не установлена :(");
            return null;
        }
        Set<String> filePaths = Arrays.stream(lab5Path.split(File.pathSeparator))
                .map(String::trim)
                .filter(path -> new File(path).isFile())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        logger.info("Файлы из переменной окружения:");
        filePaths.forEach(console::println);
        return filePaths;
    }

    /**
     * Метод, который ищет среди загруженных путей первый CSV файл
     * @return csvFilePath
     */
    public String findOrgCSVFilePath() {
        return allFilePaths.stream()
                .filter(path -> path.endsWith(".csv"))
                .findFirst()
                .orElse(null);
    }

    /**
     * Геттер для получения пути к CSV файлу
     * @return csvFilePath
     */
    public String getOrgCSVFilePath() {
        return csvFilePath;
    }

    /**
     * Метод, который записывает коллекцию в файл
     * @param collection коллекция, которую мы записываем в CSV
     */
    public void writeCollectionToCSV(Collection<Organization> collection) {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(csvFilePath))) {
            StringBuilder csvString = new StringBuilder();
            csvString.append("id,name,coordinates.x,coordinates.y,creationDate,annualTurnover,type,postalAddress\n");
            for (Organization organization : collection) {
                String[] organizationData = OrgCSVParser.toArray(organization);
                csvString.append(String.join(",", organizationData)).append("\n");
            }
            outputStream.write(csvString.toString().getBytes());
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, который преобразовывает CSV-строку в коллекцию
     * @param csvString CSV-строка
     * @return коллекция
     */
    private LinkedHashSet<Organization> convertCSVToCollection(String csvString) {
        try {
            StringReader stringReader = new StringReader(csvString);
            CSVReader csvReader = new CSVReader(stringReader);
            LinkedHashSet<Organization> collection = new LinkedHashSet<>();
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                Organization organization = OrgCSVParser.fromArray(record, logger);
                if (organization != null && organization.validate()) {
                    collection.add(organization);
                } else {
                    logger.error("Ошибка валидации организации: " + Arrays.toString(record));
                }
            }
            csvReader.close();
            return collection;
        } catch (Exception e) {
            logger.error("Ошибка при десериализации данных из CSV: " + e.getMessage());
            return null;
        }
    }

    /**
     * Метод, который считывает коллекцию из файла
     * @param collection - считанная коллекция
     */
    public void readCollectionFromCSV(Collection<Organization> collection) {
        logger.info("Происходит чтение файла: " + getOrgCSVFilePath());
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csvFilePath))) {
            BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            boolean isFirstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                stringBuilder.append(line).append("\n");
            }
            LinkedHashSet<Organization> organizations = convertCSVToCollection(stringBuilder.toString());
            collection.clear();
            if (organizations != null) {
                collection.addAll(organizations);
                if (!collection.isEmpty()) {
                    logger.info("Коллекция успешно загружена из файла");
                } else {
                    logger.error("В файле не найдена подходящая коллекция");
                }
            } else {
                logger.error("Ошибка при загрузке коллекции из CSV");
            }
        } catch (FileNotFoundException e) {
            logger.error("Файл не был найден");
        } catch (IOException e) {
            logger.error("Ошибка при чтении из файла");
        }
    }
}
