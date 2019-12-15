package by.mrf1n.model.request;

import by.mrf1n.model.CommandType;
import lombok.Data;

import java.io.Serializable;

/**
 * Минимальный обхект общения клиента с сервером без тела(только комманда)
 */

@Data
public class EmptyContract implements Serializable {
    private CommandType commandType;
}
