package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class Update extends ServerCommand {

    public Update(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.update(arg.getArgument(), arg.getHumanBeing(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.UPDATE.title + " {element} : обновить значение элемента коллекции, " +
                "id которого равен заданному";    }
}
