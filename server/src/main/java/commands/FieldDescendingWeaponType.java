package commands;

import command.CommandsEnum;
import listening.Request;
import listening.Response;
import server.ServerReceiver;

import java.util.Optional;

//todo  реализован
public class FieldDescendingWeaponType extends ServerCommand {

    public FieldDescendingWeaponType(ServerReceiver serverReceiver) {
        super(serverReceiver);
    }

    @Override
    public Optional<Response> execute(Request arg) {
        return Optional.of(serverReceiver.fieldDescendingWeaponType());
    }

    @Override
    public String getHelp() {
        return CommandsEnum.PRINT_FIELD_DESCENDING_WEAPON_TYPE.title + " : вывести все  коллекции в строковом представлении";
    }
}
