package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

public class RemoveLower extends ServerCommand {

    public RemoveLower(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.removeLower(arg.getHumanBeing(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.REMOVE_LOWER.title + " {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
