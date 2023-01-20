package base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HumanBeing implements Comparable<HumanBeing>, Serializable {

    private static final long serialVersionUID = -3000033697508215511L;

    private static final Map<String, ? super Number> limitation = new HashMap<>();

    static {
        limitation.put("id", 0);
        limitation.put("coordinateX", -428d);
    }
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть
    //уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле может быть null
    private Double impactSpeed; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле может быть null
    private String login;


    public HumanBeing(String name, Coordinates coordinates, boolean realHero, Boolean hasToothpick, Double impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }
    public HumanBeing(String name, Coordinates coordinates,Date creationDate, boolean realHero, Boolean hasToothpick, Double impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        setCreationDate(creationDate);
        this.car = car;
    }

    public HumanBeing(Integer id, String name,  double x, double y, Date creationDate, boolean realHero,
                      Boolean hasToothpick, Double impactSpeed, String weaponType, String mood, boolean carCool, String login) {
        this.id = id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        setCreationDate(creationDate);
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = WeaponType.fromString(weaponType);
        this.mood = Mood.fromString(mood);
        this.car = new Car(carCool);
        this.login = login;
    }



    //TODO
    @Override
    public String toString() {
        return "[" + "id=" + id + ", name='" + name + ", impactSpeed='" + impactSpeed + '\'' + ", user=" + login + ']';
    }

    @Override
    public int compareTo(HumanBeing anotherHumanBeing) {
        return this.name.compareTo(anotherHumanBeing.getName());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
    //todo
    public void setCreationDate(Date creationDate) {
        this.creationDate =  creationDate;
    }

    public boolean getRealHero() {
        return realHero;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public Boolean getHasToothpick() {
            return hasToothpick;

    }

    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public Double getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;

    }

    public String login() {
        return login;
    }

    public static Map<String, ? super Number> getLimitation() {
        return limitation;
    }

}
