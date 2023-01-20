package commands;

import base.HumanBeing;
import client.ClientReceiver;
import command.CommandsEnum;
import listening.Request;

import java.util.Optional;

//TODO count_by_impact_speed impactSpeed : вывести количество элементов, значение поля impactSpeed которых равно заданному
public class CountByImpactSpeed extends ClientCommand {
    public CountByImpactSpeed(ClientReceiver clientReceiver) {
        super(clientReceiver);
    }

    @Override
    public Optional<Request> execute(String arg) {
        if (arg == null) {
            System.out.println(CommandsEnum.COUNT_BY_IMPACT_SPEED.title + ": требуется аргумент:");
            return Optional.empty();
        }
        return clientReceiver.countByImpactSpeed(arg);
    }
}
