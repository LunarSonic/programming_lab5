package LunarSonic.objects;
import LunarSonic.utility.Validatable;

public class Coordinates implements Validatable {
    private final Float x; //Значение поля должно быть больше -947, Поле не может быть null
    private final Long y; //Поле не может быть null

    public Coordinates(Float x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean validate() {
        if(x == null) return false;
        if(y == null) return false;
        return (x >= -947);
    }
}
