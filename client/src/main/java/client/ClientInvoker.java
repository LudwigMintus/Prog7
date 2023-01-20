package client;

import command.CommandsEnum;
import commands.*;
import listening.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientInvoker {
    private final Map<String, ClientCommand> commandMap = new HashMap<>();

    private void register(String commandName, ClientCommand command) {
        commandMap.put(commandName, command);
    }

    public ClientInvoker(ClientReceiver clientReceiver) {

        for (CommandsEnum command : CommandsEnum.values()) {
            Optional<ClientCommand> optional = create(clientReceiver, command);
            optional.ifPresent(clientCommand -> register(command.title, clientCommand));
        }
    }

    public Optional<Request> check(String commandName, String argument) {
        if (this.commandMap.containsKey(commandName))
            return this.commandMap.get(commandName).execute(argument);
        System.out.println("Введённой команды не существует");
        return Optional.empty();
    }


    private Optional<ClientCommand> create(ClientReceiver clientReceiver, CommandsEnum command) {
        switch (command) {
        case ADD:
            return Optional.of(new Add(clientReceiver));
        case ADD_IF_MIN:
            return Optional.of(new AddIfMin(clientReceiver));
        case CLEAR:
            return Optional.of(new Clear(clientReceiver));
        case EXECUTE_SCRIPT:
            return Optional.of(new ExecuteScript(clientReceiver));
        case EXIT:
            return Optional.of(new Exit(clientReceiver));
        case FILTER_STARTS_WITH_NAME:
            return Optional.of(new FilterStartsWithName(clientReceiver));
        case HELP:
            return Optional.of(new Help(clientReceiver));
        case INFO:
            return Optional.of(new Info(clientReceiver));
        case ADD_IF_MAX:
            return Optional.of(new AddIfMax(clientReceiver));
        case COUNT_BY_IMPACT_SPEED:
            return Optional.of(new CountByImpactSpeed(clientReceiver));
        case REMOVE_BY_ID:
            return Optional.of(new RemoveById(clientReceiver));
            case PRINT_FIELD_DESCENDING_WEAPON_TYPE:
            return Optional.of(new FieldDescendingWeaponType(clientReceiver)); // todo
        case REMOVE_LOWER:
            return Optional.of(new RemoveLower(clientReceiver));
        case SHOW:
            return Optional.of(new Show(clientReceiver));
        case UPDATE:
            return Optional.of(new Update(clientReceiver));
        case AUTHORIZATION:
        default:
            return Optional.empty();
        }
    }

}
