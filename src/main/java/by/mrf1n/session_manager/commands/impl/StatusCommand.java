package by.mrf1n.session_manager.commands.impl;

import by.mrf1n.model.CommandType;
import by.mrf1n.model.request.BodyContract;
import by.mrf1n.session_manager.commands.SocketCommand;
import by.mrf1n.session_manager.model.UserSession;
import by.mrf1n.session_manager.service.StatusService;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Реализация класса выполнения комманды Статус
 */

@Component
public class StatusCommand extends SocketCommand {

    private final StatusService statusService;

    public StatusCommand(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public CommandType execute(UserSession userSession) {
        BodyContract body = new BodyContract();
        body.setCommandType(CommandType.IDLE);
        body.setBody("Please wait.");
        try {
            if (statusService.checkBanIp(userSession)) {
                body.setCommandType(CommandType.BAN_IP);
                body.setBody("You banned by IP.");
            } else if (statusService.checkKick(userSession)) {
                body.setCommandType(CommandType.KICK);
                body.setBody("You Kicked.");
            } else if (statusService.checkName(userSession)) {
                body.setCommandType(CommandType.JOB);
                body.setBody("You can do job.");
            }
            objectOutputStream.writeObject(body);
            return body.getCommandType();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.getCommandType();
    }
}
