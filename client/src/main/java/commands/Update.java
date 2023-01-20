package commands;

import base.HumanBeing;
import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

public class Update extends ClientCommand {

    public Update(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(CommandsEnum.UPDATE.title + ": неверный формат команды");
            System.out.println("id > " + HumanBeing.getLimitation().get("id"));
            return Optional.empty();
        }
        return clientReceiver.update(arg);
    }
}
