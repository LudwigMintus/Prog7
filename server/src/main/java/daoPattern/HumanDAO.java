package daoPattern;

import base.HumanBeing;

import java.util.Set;

public interface HumanDAO {

    int create(HumanBeing humanBeing, String login);

    Set<HumanBeing> readAll();

    boolean updateById(int id, HumanBeing humanBeing, String login);

    boolean clearByUser(String login);

    boolean removeById(int id, String login);

    boolean removeLower(HumanBeing humanBeing, String login);

}
