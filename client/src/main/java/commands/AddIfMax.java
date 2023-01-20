package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class AddIfMax extends ClientCommand {

    public AddIfMax(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.ADD_IF_MAX.title + ": неверный формат команды");
            return Optional.empty();
        }
        return clientReceiver.addIfMax();

        // return clientReceiver.addIfMin();
    }
}
