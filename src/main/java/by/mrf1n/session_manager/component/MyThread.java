package by.mrf1n.session_manager.component;

import by.mrf1n.model.CommandType;
import by.mrf1n.model.request.BodyContract;
import by.mrf1n.session_manager.commands.SocketCommand;
import by.mrf1n.session_manager.commands.impl.AnswerCommand;
import by.mrf1n.session_manager.commands.impl.JobCommand;
import by.mrf1n.session_manager.commands.impl.QuitCommand;
import by.mrf1n.session_manager.commands.impl.StatusCommand;
import by.mrf1n.session_manager.model.UserIp;
import by.mrf1n.session_manager.model.UserSession;
import by.mrf1n.session_manager.repository.UserIpRepository;
import by.mrf1n.session_manager.repository.UserSessionRepository;
import by.mrf1n.session_manager.service.ServerService;
import by.mrf1n.session_manager.service.ServiceService;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import static by.mrf1n.model.CommandType.BAN_IP;
import static by.mrf1n.model.CommandType.KICK;
import static by.mrf1n.model.CommandType.QUIT;

@Component
@Scope("prototype")
@Getter
@Setter
public class MyThread implements Runnable {

    private final StatusCommand statusCommand;
    private final JobCommand jobCommand;
    private final QuitCommand quitCommand;
    private final AnswerCommand answerCommand;

    private Socket client;
    private UserSession userSession;
    private final UserSessionRepository userSessionRepository;
    private final UserIpRepository userIpRepository;
    private int number;
    private Map<CommandType, SocketCommand> commands;

    public MyThread(StatusCommand statusCommand, QuitCommand quitCommand,
                    JobCommand jobCommand, AnswerCommand answerCommand,
                    UserSessionRepository userSessionRepository, UserIpRepository userIpRepository) {
        this.statusCommand = statusCommand;
        this.jobCommand = jobCommand;
        this.quitCommand = quitCommand;
        this.answerCommand = answerCommand;
        this.userSessionRepository = userSessionRepository;
        this.userIpRepository = userIpRepository;
        commands = ImmutableMap.
                <CommandType, SocketCommand>builder()
                .put(CommandType.STATUS, statusCommand)
                .put(CommandType.RUN_JOB, jobCommand)
                .put(CommandType.RUN_ANSWER, answerCommand)
                .put(QUIT, quitCommand)
                .build();
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            saveUserSession(userSession);
            while (!client.isClosed()) {
                BodyContract bodyContract = (BodyContract) inputStream.readObject();
                SocketCommand socketCommand = commands.get(bodyContract.getCommandType());
                socketCommand.setObjectInputStream(inputStream);
                socketCommand.setObjectOutputStream(outputStream);
                socketCommand.setBody(bodyContract.getBody());
                CommandType type = socketCommand.execute(userSession);
                if (QUIT.equals(bodyContract.getCommandType())
                        || ServiceService.anyMatchCommandType(type, QUIT, BAN_IP, KICK)) {
                    System.out.println(bodyContract.getBody());
                    break;
                }
            }
            userSessionRepository.deleteById(userSession.getId());
            close(outputStream, inputStream);
        } catch (IOException | ClassNotFoundException ex) {
            userSessionRepository.deleteById(userSession.getId());
            ex.printStackTrace();
        }
    }

    private void close(ObjectOutputStream dataOutputStream, ObjectInputStream dataInputStream) throws IOException {
        System.out.println("Client disconnected");
        System.out.println();
        dataInputStream.close();
        dataOutputStream.close();
        client.close();
        --ServerService.number;
    }

    private void saveUserSession(UserSession userSession) {
        if (!userIpRepository.findById(userSession.getIpAddr()).isPresent()) {
            UserIp userIp = new UserIp();
            userIp.setIpAddr(userSession.getIpAddr());
            userIpRepository.save(userIp);
        }
        this.userSession = userSessionRepository.save(userSession);
    }
}
