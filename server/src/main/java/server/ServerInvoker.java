package server;

import command.CommandsEnum;
import commands.*;
import commands.ServerCommand;
import listening.Request;
import listening.Response;

import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ServerInvoker {

    private final HashMap<String, ServerCommand> commandMap = new HashMap<>();

    private void register(String commandName, ServerCommand command) {
        commandMap.put(commandName, command);
    }

    public ServerInvoker(ServerReceiver serverReceiver) {
        for (CommandsEnum command : CommandsEnum.values()) {
            Optional<ServerCommand> optional = create(serverReceiver, command);
            optional.ifPresent(serverCommand -> register(command.title, serverCommand));
        }
    }

    public Optional<Response> execute(Request request){
        String commandName = request.getCommandName();
        if (request.getLogin() == null || request.getPassword() == null
                || request.getLogin().equals("") && !commandName.equals("authorization")) {
            return Optional.of(new Response("Выполнение команд не доступно неавторизованным пользователям.\\nВведите authorization, чтобы зарегистрироваться в системе\""));
        }
        return this.commandMap.get(commandName).execute(request);
    }

    public HashMap<String, ServerCommand> getCommandMap() {
        return this.commandMap;
    }

    private Optional<ServerCommand> create(ServerReceiver serverReceiver, CommandsEnum command) {
        switch (command) {
        case ADD:
            return Optional.of(new Add(serverReceiver));
        case ADD_IF_MIN:
            return Optional.of(new AddIfMin(serverReceiver));
        case CLEAR:
            return Optional.of(new Clear(serverReceiver));
        case EXECUTE_SCRIPT:
            return Optional.of(new ExecuteScript(serverReceiver));
        case EXIT:
            return Optional.of(new Exit(serverReceiver));
        case FILTER_STARTS_WITH_NAME:
            return Optional.of(new FilterStartsWithName(serverReceiver));
        case HELP:
            return Optional.of(new Help(serverReceiver, getCommandMap()));
        case INFO:
            return Optional.of(new Info(serverReceiver));
        case COUNT_BY_IMPACT_SPEED:
            return Optional.of(new CountByImpactSpeed(serverReceiver));
        case ADD_IF_MAX:
            return Optional.of(new AddIfMax(serverReceiver));
        case REMOVE_BY_ID:
            return Optional.of(new RemoveById(serverReceiver));
        case PRINT_FIELD_DESCENDING_WEAPON_TYPE:
            return Optional.of(new FieldDescendingWeaponType(serverReceiver));
        case REMOVE_LOWER:
            return Optional.of(new RemoveLower(serverReceiver));
        case SHOW:
            return Optional.of(new Show(serverReceiver));
        case UPDATE:
            return Optional.of(new Update(serverReceiver));
        case AUTHORIZATION:
            return Optional.of(new Authorization(serverReceiver));
        default:
            return Optional.empty();
        }
    }

}
