package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class Info extends ClientCommand {
    public Info(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.INFO.title + ": неверный формат команды");
            return Optional.empty();
        }
        return Optional.of(new Request("info"));
    }
}
