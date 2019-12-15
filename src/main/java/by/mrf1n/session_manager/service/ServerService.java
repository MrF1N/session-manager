package by.mrf1n.session_manager.service;

import by.mrf1n.session_manager.component.MyThread;
import by.mrf1n.session_manager.model.UserSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Реализация класса многопоточного сервера
 */

@Service
public class ServerService {

    private final TaskExecutor taskExecutor;
    private final ApplicationContext applicationContext;
    public static int number = 0;

    public ServerService(@Qualifier("taskExecutor") TaskExecutor taskExecutor, ApplicationContext applicationContext) {
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
    }

    public void executeServer() {
        try (ServerSocket serverSocket = new ServerSocket(3345);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server socket created, command console reader for listen to server commands");
            while (!serverSocket.isClosed()) {
                if (bufferedReader.ready()) {
                    System.out.println("Main Server found any messages in channel, let's look at them.");
                }
                Socket client = serverSocket.accept();
                MyThread myThread = applicationContext.getBean(MyThread.class);
                myThread.setClient(client);
                myThread.setNumber(++number);
                UserSession userSession = createUserSession(client.getInetAddress().toString().split("/")[1]);
                myThread.setUserSession(userSession);
                taskExecutor.execute(myThread);
                System.out.print(new Date().toString() + " " + client.getInetAddress() + " Connection accepted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserSession createUserSession(String ipAddr) {
        UserSession userSession = new UserSession();
        userSession.setIpAddr(ipAddr);
        return userSession;
    }
}
