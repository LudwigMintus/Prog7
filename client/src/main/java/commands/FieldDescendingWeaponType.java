package commands;

import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

//TODO Реализовать
public class FieldDescendingWeaponType extends ClientCommand {
    public FieldDescendingWeaponType(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }
    @Override
    public Optional<Request> execute(String arg) {
        if (arg != null) {
            System.out.println(CommandsEnum.PRINT_FIELD_DESCENDING_WEAPON_TYPE.title + ": " + ": неверный формат команды");
            return Optional.empty();
        }
        return Optional.of(new Request("print_field_descending_weapon_type"));
    }



}