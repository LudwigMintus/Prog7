package client;

import input.Creator;
import input.Typer;
import input.Validator;
import listening.Request;

import java.util.Optional;
import java.util.ResourceBundle;

public class ClientReceiver {

    private final Creator creator;

    public ClientReceiver() {
        this.creator = new Creator(new Typer());
    }

    public Optional<Request> removeLower() {
        System.out.println("Создайте элемент. Из коллекции будут удалены все ваши элементы, меньшие, чем заданный.");
        return Optional.of(new Request("remove_lower", creator.createHumanBeing()));
    }

    public Optional<Request> addIfMin() {
        System.out.println("Создайте элемент. Если его значение меньше, чем у наименьшего элемента в базе данных, то он будет добавлен.");
        return Optional.of(new Request("add_if_min", creator.createHumanBeing()));
    }

    public Optional<Request> addIfMax() {
        System.out.println(
                "Создайте элемент. Если его значение больше, чем у наибольшего элемента в базе данных, то он будет добавлен");
        return Optional.of(new Request("add_if_max", creator.createHumanBeing()));
    }

    public Optional<Request> add() {
        return Optional.of(new Request("add", creator.createHumanBeing()));
    }

    public Optional<Request> update(String arg) {
        Long id = Validator.validateId(arg);
        System.out.println(
                "Создайте человека, который заменит человека с указанным id (если существует и у вас есть права на изменение).\n");
        return Optional.of(new Request("update", arg, creator.createHumanBeing()));
    }
    public Optional<Request> removeById(String arg) {
        Long id = Validator.validateId(arg);
        if (id == null) {
            System.out.println("Аргумент id передан неверно. id не может быть отрицательным числом или нулём");
            return Optional.empty();
        }
        return Optional.of(new Request("remove_by_id", arg));
    }
    public Optional<Request> countByImpactSpeed(String arg) {
        Double agglomeration = Validator.validateTypeImpactSpeed(arg);
        if (null == agglomeration) {
            System.out.println("Аргумент передан неверно. Он не может быть отрицательным числом или нулём");
            return Optional.empty();
        }
        return Optional.of(new Request("count_by_impact_speed", arg));
    }// Todo Request("count_impact_by_speed", arg)); не забудь

    public Optional<Request> FieldDescendingWeaponType(String arg) {
        Long id = Validator.validateId(arg);
        if (id == null) {
            System.out.println("Аргумент id передан неверно. id не может быть отрицательным числом или нулём");
            return Optional.empty();
        }
        return Optional.of(new Request("field_descending_weapoType", arg));
        //togo реализовать аргумент не нужен
    }
}
