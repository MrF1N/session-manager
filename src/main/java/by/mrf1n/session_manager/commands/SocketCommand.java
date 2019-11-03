package by.mrf1n.session_manager.commands;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Data
@Component
public abstract class SocketCommand implements Command {
    protected ObjectOutputStream objectOutputStream;
    protected ObjectInputStream objectInputStream;
    protected Object body;
}
