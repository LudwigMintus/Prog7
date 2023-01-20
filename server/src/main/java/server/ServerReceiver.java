package server;

import base.HumanBeing;
import base.WeaponType;
import commands.ServerCommand;
import listening.Response;
import service.HumanService;
import service.UserService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ServerReceiver {

    Set<HumanBeing> collection = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Date creationDate;
    private final HumanService humanService = new HumanService();
    private final UserService userService = new UserService();
    private static final ReentrantLock usersLock = new ReentrantLock();

    public ServerReceiver() {
        this.creationDate = new Date();
    }

    public Response add(HumanBeing humanBeing, String login) {
        int id = humanService.create(humanBeing, login);
        if (id > 0) {
            humanBeing.setId(id);
            humanBeing.setLogin(login);
            collection.add(humanBeing);
            return new Response("Человек добавлен, id = " + id);
        } else {
            return new Response("Ошибка добавления:\nКоллекция не предполагает хранение людей с одинаковыми именами");
        }
    }

    public Response addIfMin(HumanBeing humanBeing, String login) {
        try {
            HumanBeing humanBeing1 = collection.stream().min(Comparator.comparing(HumanBeing::getImpactSpeed)).get();
            if (humanBeing1.getImpactSpeed() > humanBeing.getImpactSpeed()) {
                int id = humanService.create(humanBeing, login);
                humanBeing.setId(id);
                humanBeing.setLogin(login);
                collection.add(humanBeing);
                return new Response("Человек добавлен, id = " + id);
            }
            return new Response("Ошибка добавления:\nУсловие не выполнено");
        } catch (NoSuchElementException ex) {
            return add(humanBeing, login);
        }
    }
    public Response addIfMax(HumanBeing humanBeing, String login) {
        try {
            HumanBeing humanBeing1 = collection.stream().max(Comparator.comparing(HumanBeing::getImpactSpeed)).get();
            if (humanBeing1.getImpactSpeed() < humanBeing.getImpactSpeed()) {
                int id = humanService.create(humanBeing, login);
                humanBeing.setId(id);
                humanBeing.setLogin(login);
                collection.add(humanBeing);
                return new Response("Человек добавлен, id = " + id);
            }
            return new Response("Ошибка добавления:\nУсловие не выполнено");
        } catch (NoSuchElementException ex) {
            return add(humanBeing, login);
        }
    }

    public Response filterStartsWithName(String name) {
            return new Response(collection.stream().filter(city -> city.getName().startsWith(name)).map(HumanBeing::toString)
                    .toArray(String[]::new));

    }

    public Response info() {

        String[] information = new String[3];
        information[0] = "Тип коллекции: " + collection.getClass();
        information[1] = "Дата инициализации коллекции: " + creationDate;
        information[2] = "Количество элементов коллекции: " + collection.size();
        return new Response(information);

    }

// todo fieldDescendingWeaponType проверить работает ли
    public Response fieldDescendingWeaponType(){
        List<WeaponType> weapons = new ArrayList<>();
        for (HumanBeing humanBeing : collection){
            weapons.add(humanBeing.getWeaponType());
        }
        Collections.sort(weapons);
        return new Response(weapons.toString());
    }
    public Response show() {
            return new Response(collection.stream().map(HumanBeing::toString).toArray(String[]::new));
    }

    public Response printDescending() {

            return new Response(
                    collection.stream().sorted(Collections.reverseOrder()).map(HumanBeing::toString).toArray(String[]::new));

    }


    public Response removeById(String idStr, String login) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            return new Response("Клиент передал невалидный id.");
        }
        if (humanService.removeById(id, login)) {//Попытка добавить БД
            collection.removeIf(humanBeing -> humanBeing.getId().equals(id));//Удалили
            return new Response("Из коллекции удалены все ваши люди с заданным условием:\nid = " + id);
        } else {
            return new Response("Удаление выполнить не удалось:\n Человек с таким id не существует или он пренадлежит не вам");
        }
    }


    public Response removeLower(HumanBeing humanBeing, String login) {
            if (humanService.removeLower(humanBeing, login)) {
                collection.removeIf(humanFromColl -> humanFromColl.compareTo(humanBeing) < 0
                        && humanFromColl.getLogin().equals(login));
                return new Response("Из коллекции удалены все ваши люди с заданным условием:\nПревышают созданный");
            }
            return new Response("Вы еще не создали человека с данным условием:\nПревышают созданный");
    }

    public Response help(Map<String, ServerCommand> commandMap) {
        return new Response(commandMap.values().stream().map(ServerCommand::getHelp).toArray(String[]::new));
    }

    public Response clear(String login) {
            if (humanService.clearByUser(login)) {
                collection.removeIf(humanBeing -> humanBeing.getLogin().equals(login));
                return new Response("Из коллекции удалены все, созданные вами люди");
            }
            return new Response("Ни один элемент не был удалён");
    }

    public Response update(String idStr, HumanBeing humanBeing, String login) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            return new Response("Клиент передал невалидный id.");
        }
        if (humanService.updateById(id, humanBeing, login)) {
            collection.removeIf(humanFromColl -> humanFromColl.getId().equals(id));
            humanBeing.setId(id);
            humanBeing.setLogin(login);
            collection.add(humanBeing);
            return new Response("Поля человека успешно обновлены");
        } else {
            return new Response("Не удалось обновить поля человека:\nЧеловек с таким условием не существует или принадлежит не вам");
        }
    }

    public Response authorization(String login, String password) {
        if (login.isEmpty()) {
            return new Response("Имя пользователя не может быть пустой строкой");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            password = hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        usersLock.lock();
        try {
            if (userService.checkExists(login, password)) {
                return new Response("");
            } else if (userService.checkImpostor(login, password)) {
                return new Response("Неверный пароль для пользователя: " + login);
            } else {
                userService.create(login, password);
                return new Response("");
            }
        } finally {
            usersLock.unlock();
        }
    }

    public Response countByImpactSpeed(String impactSpeed) {
        //TODO count_by_impact_speed impactSpeed : вывести количество элементов, значение поля impactSpeed которых равно заданному
        Long count = collection.stream().filter(humanBeing -> humanBeing.getImpactSpeed().toString().equals(impactSpeed)).count();
            return new Response(count.toString());
    }


    void initCollection() {
        collection = humanService.readAll();
    }

}
