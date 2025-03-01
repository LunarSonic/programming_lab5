package LunarSonic.objects;
import LunarSonic.utility.Model;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Organization extends Model {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final long annualTurnover; //Значение поля должно быть больше 0
    private final OrganizationType type; //Поле может быть null
    private final Address postalAddress; //Поле не может быть null

    public Organization(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, long annualTurnover, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public long getAnnualTurnover() {
        return annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    @Override
    public boolean validate() {
        if(id <= 0) return false;
        if(name == null || name.isEmpty()) return false;
        if(coordinates == null) return false;
        if(creationDate == null) return false;
        if(annualTurnover <= 0 ) return false;
        return postalAddress != null;
    }

    /**
     * Метод для сравнения 2 объектов (по годовому обороту)
     * @param model объект для сравнения
     * @return annualTurnover
     */
    @Override
    public int compareTo(Model model) {
        return Long.compare(this.annualTurnover, model.getAnnualTurnover());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, annualTurnover, type, postalAddress);
    }

    @Override
    public String toString() {
        return "Организация \"" + getName() + "\", id: " + getId() + "\n" +
                "Координаты: {x: " + getCoordinates().getX() + ", y: " + getCoordinates().getY() + "}\n" +
                "Дата создания: " + getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss")) + "\n" +
                "Годовой оборот: " + getAnnualTurnover() + "\n" +
                "Тип организации: " + (getType() != null ? getType() : "не указан") + "\n" +
                "Адрес: " + (getPostalAddress() != null ? getPostalAddress().getStreet() : "не указан") + "\n";
    }
}
