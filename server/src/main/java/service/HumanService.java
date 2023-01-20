package service;

import base.HumanBeing;
import businessLogic.Database;
import daoPattern.HumanDAO;
import serverLogger.ServerLogger;

import java.sql.*;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HumanService extends Database implements HumanDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private final Logger LOGGER = ServerLogger.getLogger();

    public HumanService() {
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.INIT.QUERY);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
           System.out.println(throwables.getMessage());
            LOGGER.log(Level.SEVERE, "Ошибка при обращении к базе данных при создании таблицы humans",
                    new RuntimeException());
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    @Override
    public int create(HumanBeing humanBeing, String login) {
        int result = -1;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.INSERT.QUERY);
            statement.setString(1, humanBeing.getName());
            statement.setDouble(2, humanBeing.getCoordinates().getX());
            statement.setDouble(3, humanBeing.getCoordinates().getY());
            statement.setDate(4, new java.sql.Date(humanBeing.getCreationDate().getTime()));
            statement.setBoolean(5, humanBeing.getRealHero());
            statement.setBoolean(6, humanBeing.getHasToothpick());
            statement.setDouble(7, humanBeing.getImpactSpeed());
            statement.setString(8, humanBeing.getWeaponType().toString());
            statement.setString(9, humanBeing.getMood().toString());
            statement.setBoolean(10, humanBeing.getCar().getCool());
            statement.setString(11, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("id");
                return result;
            } else {
                return result;
            }
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при обращении к базе данных при добавлении человека.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public Set<HumanBeing> readAll() {
        Set<HumanBeing> result = new LinkedHashSet<>();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.READ_ALL.QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                Date crDate = new Date(resultSet.getDate("creation_date").getTime());
                boolean realHero =resultSet.getBoolean("real_hero");
                Boolean hasToothpick = resultSet.getBoolean("has_toothpick");
                Double impactSpeed = resultSet.getDouble("impact_speed");
                String weaponType = resultSet.getString("weapon_type");
                String mood = resultSet.getString("mood");
                boolean carCool = resultSet.getBoolean("car_cool");
                String login = resultSet.getString("login");
                HumanBeing humanBeing = null;
                try {
                    humanBeing = new HumanBeing(id, name, x, y, crDate, realHero, hasToothpick, impactSpeed,
                            weaponType, mood, carCool, login);
                } catch (NullPointerException ex) {
                    LOGGER.log(Level.WARNING, "В базе данных обнаружен невалидный человек. Он немедленно будет удален.");
                    deleteInvalidHuman(name);
                }
                if (humanBeing != null && !result.add(humanBeing)) {
                    LOGGER.warning(
                            "В базе данных обнаружены люди с одинаковым названием. Они немедленно будут удалены.");
                    deleteInvalidHuman(name);
                }
            }
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при обращении к базе данных при чтении содержимого.");
            return new LinkedHashSet<>();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    private void deleteInvalidHuman(String name) {
        try {
            statement = connection.prepareStatement(SQLHuman.REMOVE_BY_NAME.QUERY);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при удалении невалидного человека из базы данных.");
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean updateById(int id, HumanBeing humanBeing, String login) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.UPDATE_BY_ID.QUERY);
            statement.setString(1, humanBeing.getName());
            statement.setDouble(2, humanBeing.getCoordinates().getX());
            statement.setDouble(3, humanBeing.getCoordinates().getY());
            statement.setDate(4, new java.sql.Date(humanBeing.getCreationDate().getTime()));
            statement.setBoolean(5, humanBeing.getRealHero());
            statement.setBoolean(6, humanBeing.getHasToothpick());
            statement.setDouble(7, humanBeing.getImpactSpeed());
            statement.setString(8, humanBeing.getWeaponType().toString());
            statement.setString(9, humanBeing.getMood().toString());
            statement.setBoolean(10, humanBeing.getCar().getCool());
            statement.setInt(11, id);
            statement.setString(12, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при обращении к базе данных при обновлении человека.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean removeById(int id, String login) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.REMOVE_BY_ID.QUERY);
            statement.setInt(1, id);
            statement.setString(2, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при обращении к базе данных при удалении человека по его id.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean clearByUser(String login) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.CLEAR_BY_USER.QUERY);
            statement.setString(1, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при обращении к базе данных при очистке коллекции пользователем.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean removeLower(HumanBeing humanBeing, String login) {
        boolean result = false;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLHuman.REMOVE_LOWER.QUERY);
            statement.setString(1, humanBeing.getName());
            statement.setString(2, login);
            result = statement.executeQuery().next();
        } catch (SQLException throwables) {
            LOGGER.warning("Ошибка при обращении к базе данных при удалению людей меньших, чем заданный.");
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    private enum SQLHuman {

        INSERT("insert into humans (name, x, y, creation_date, real_hero, has_toothpick, impact_speed, weapon_type, "
                + "mood, car_cool, login) "
                + "values (?,?,?,?,?,?,?,?,?,?,?) returning id;"),
        READ_ALL("select * from humans;"),
        REMOVE_BY_NAME("delete from humans where name = ?;"),
        UPDATE_BY_ID("update humans set name = ?, x = ?, y = ?, creation_date = ?, real_hero = ?, has_toothpick = ?, "
                + "impact_speed = ?, weapon_type = ?, mood = ?, car_cool = ?, where id = ? and login = ? returning id;"),
        REMOVE_BY_ID("delete from humans where id = ? and login = ? returning id;"),
        CLEAR_BY_USER("delete from humans where login = ? returning id;"),
        REMOVE_LOWER("delete from humans where name < ? and login = ? returning id;"),
        INIT("create table if not exists humans(id serial not null primary key, name varchar(50) not null unique, "
                + "x double precision not null, y double precision not null, creation_date date, real_hero boolean , " +
                "has_toothpick boolean, impact_speed double precision, weapon_type varchar(13), mood varchar(13), "
                + " car_cool boolean, login varchar(20), "
                + "foreign key (login) references users (login));");
        String QUERY;

        SQLHuman(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
