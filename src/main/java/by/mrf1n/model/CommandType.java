package by.mrf1n.model;

/**
 * Клманды общения клиента и сервера(аналог кодов в HTTP)
 */

public enum CommandType {
    STATUS,

    IDLE,
    JOB,
    QUIT,
    BAN_IP,
    KICK,

    RUN_JOB,

    RESPONSE_JOB,
    CANT,

    RUN_ANSWER,

    RESPONSE_ANSWER
}
