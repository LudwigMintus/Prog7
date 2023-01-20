package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

//todo
public class AddIfMax extends ServerCommand {
    public AddIfMax(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.addIfMax(arg.getHumanBeing(), arg.getLogin()));
    }

    @Override
    public String getHelp() {
        return CommandsEnum.ADD_IF_MAX.title + " {element} : добавить новый элемент" +
                " в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

}

