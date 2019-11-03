package by.mrf1n.session_manager.commands.impl;

import by.mrf1n.model.CommandType;
import by.mrf1n.session_manager.commands.SocketCommand;
import by.mrf1n.session_manager.model.UserSession;
import org.springframework.stereotype.Component;

@Component
public class QuitCommand extends SocketCommand {
    @Override
    public CommandType execute(UserSession userSession) {
        return CommandType.QUIT;
    }
}
