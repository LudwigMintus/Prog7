package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class CountByImpactSpeed extends ServerCommand {

    public CountByImpactSpeed(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.countByImpactSpeed(arg.getArgument()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.COUNT_BY_IMPACT_SPEED.title + ": " + "вывести количество элементов," +
                " значение поля IMPACT_SPEED которых больше заданному";
    }
}