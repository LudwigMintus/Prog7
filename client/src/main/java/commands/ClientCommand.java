package commands;

import client.ClientReceiver;
import command.AbstractCommand;
import listening.Request;

import java.util.ResourceBundle;

public abstract class ClientCommand extends AbstractCommand<String, Request> {

    protected ClientReceiver clientReceiver;

    public ClientCommand(ClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    @Override
    public String getHelp() {
        return "";
    }
}
