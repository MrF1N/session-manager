package by.mrf1n.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Минимальный обхект общения клиента с сервером содержащее тело
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BodyContract extends EmptyContract {
    private Object body;
}
