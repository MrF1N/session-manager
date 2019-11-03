package by.mrf1n.session_manager.component;

import by.mrf1n.session_manager.service.ServerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerTool implements CommandLineRunner {

    private final ServerService serverService;

    public ServerTool(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public void run(String... args) {
        serverService.executeServer();
    }

}
