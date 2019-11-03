package by.mrf1n.session_manager.commands.impl;

import by.mrf1n.model.CommandType;
import by.mrf1n.model.request.BodyContract;
import by.mrf1n.session_manager.commands.SocketCommand;
import by.mrf1n.session_manager.model.UserSession;
import by.mrf1n.session_manager.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JobCommand extends SocketCommand {

    private final StatusService statusService;

    public JobCommand(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public CommandType execute(UserSession userSession) {
        BodyContract body = new BodyContract();
        body.setCommandType(CommandType.QUIT);
        body.setBody("Please wait.");
        try {
            if (statusService.checkBanIp(userSession)) {
                body.setCommandType(CommandType.BAN_IP);
                body.setBody("You banned by IP.");
            } else if (statusService.checkKick(userSession)) {
                body.setCommandType(CommandType.KICK);
                body.setBody("You Kicked.");
            } else if (!statusService.checkName(userSession)) {
                body.setCommandType(CommandType.CANT);
                body.setBody("You can't do job.");
            } else if (statusService.checkName(userSession)) {
                body.setCommandType(CommandType.RESPONSE_JOB);
                body.setBody("Please Enter line for count the spaces.");
            }
            objectOutputStream.writeObject(body);
            return body.getCommandType();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.getCommandType();
    }
}
