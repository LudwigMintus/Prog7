package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class AddIfMin extends ClientCommand {

    public AddIfMin(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.ADD.title + ": неверный формат команды");
            return Optional.empty();
        }
        return clientReceiver.addIfMin();
    }
}
