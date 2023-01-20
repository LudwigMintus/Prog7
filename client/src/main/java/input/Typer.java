package input;

import base.*;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Typer {
    private final Scanner scanner = new Scanner(System.in);

    public String typeName() {
        System.out.print("Имя Человека:\n>");
        String name = Validator.validateName(scanner.nextLine());
        if (name == null) {
            System.out.println("Нарушен формат ввода: пустая строка");
            return typeName();
        }
        return name;
    }

    public Coordinates typeCoordinates() {
        System.out.print("Координаты [x;y]:\n>");
        Coordinates coordinates = Validator.validateCoordinates(scanner.nextLine());
        if (coordinates == null) {
            System.out.println("Нарушен формат ввода: ");
            System.out.println("между x и y необходима ';'");
            System.out.println("y > " + HumanBeing.getLimitation().get("coordinateY") + ".");
            return typeCoordinates();
        }
        return coordinates;
    }

    public boolean typeRealHero() {
        System.out.print("Введите 'Y' если он настоящий герой, в ином случае введите любой другой символ:\n>");
        String realHero = scanner.nextLine();
        return Objects.equals(realHero, "Y");
    }

    public Boolean typeHasToothpick() {
        System.out.print("Введите 'Y' если у него есть зубочистка  в ином случае введите любой другой символ\n>");
        String hasToothPick = (scanner.nextLine());
        return Objects.equals(hasToothPick, "Y");
    }

    public Double typeImpactSpeed() {
        System.out.print("Введите скорость удара:\n>");
        Double impactSpeed = Validator.validateTypeImpactSpeed(scanner.nextLine());
        if (impactSpeed == null) {
            System.out.println("Нарушен формат, повторите ввод");
            return typeImpactSpeed();
        }
        return impactSpeed;
    }

    public WeaponType typeWeaponType() {
        System.out.println("Выберите цифру(тип оружия");
        int i = 1;
        for (WeaponType standard : WeaponType.values()) {
            System.out.println(i++ + ") " + standard.toString());
        }
        System.out.print(">");
        WeaponType weaponType = Validator.validateWeaponType(scanner.nextLine());
        if (weaponType == null) {
            System.out.println("Нарушен формат ввода");
            return typeWeaponType();
        }
        return weaponType;
    }
    public Mood typeMood() {
        System.out.println("Выберите цифру(тип настроения)");
        int i = 1;
        for (Mood standard : Mood.values()) {
            System.out.println(i++ + ") " + standard.toString());
        }
        System.out.print(">");
        Mood mood = Validator.validateMood(scanner.nextLine());
        if (mood == null) {
            System.out.println("Нарушен формат ввода");
            return typeMood();
        }
        return mood;
    }
    public Car typeCar() {
        System.out.print("Введите 'Y' если у него есть машина в ином случае введите любой другой символ: \n>");
        String car = (scanner.nextLine());
        if (Objects.equals(car, "Y")) {
            return new Car(true);
        }else{
            return new Car(false);
        }
    };

}
