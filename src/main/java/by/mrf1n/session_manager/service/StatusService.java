package by.mrf1n.session_manager.service;

import by.mrf1n.session_manager.model.UserIp;
import by.mrf1n.session_manager.model.UserSession;
import by.mrf1n.session_manager.repository.UserIpRepository;
import by.mrf1n.session_manager.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final UserSessionRepository userSessionRepository;
    private final UserIpRepository userIpRepository;

    public StatusService(UserSessionRepository userSessionRepository, UserIpRepository userIpRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userIpRepository = userIpRepository;
    }

    /**
     * @return "Idle" -
     */
    public boolean checkBanIp(UserSession userSession) {
        UserIp userIp = userIpRepository.findById(userSession.getIpAddr()).orElse(null);
        if (userIp != null) {
            return userIp.isBannedByIp();
        }
        return false;
    }

    public boolean checkKick(UserSession userSession) {
        UserSession userSes = userSessionRepository.findById(userSession.getId()).orElse(null);
        if (userSes != null) {
            return userSes.isBannedBySession();
        }
        return false;
    }

    public boolean checkName(UserSession userSession) {
        UserIp userIp = userIpRepository.findById(userSession.getIpAddr()).orElse(null);
        if (userIp != null) {
            return userIp.getName() != null && !userIp.getName().isEmpty();
        }
        return false;
    }
}
