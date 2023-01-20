package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Exit extends ClientCommand {
    public Exit(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.EXIT + ": нет аргументов");
            return Optional.empty();
        }
        System.out.println("Спасибо за работу. До свидания.");
        throw new NoSuchElementException();
    }
}
