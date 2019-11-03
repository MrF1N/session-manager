package by.mrf1n.session_manager.service;

import by.mrf1n.model.CommandType;

public class ServiceService {

    public static boolean anyMatchCommandType(CommandType commandType, CommandType ... types) {
        for (CommandType type : types) {
            if(type.equals(commandType)){
                return true;
            }
        }
        return false;
    }
}
