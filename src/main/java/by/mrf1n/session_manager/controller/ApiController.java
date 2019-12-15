package by.mrf1n.session_manager.controller;

import by.mrf1n.session_manager.model.UserIp;
import by.mrf1n.session_manager.model.UserSession;
import by.mrf1n.session_manager.repository.UserIpRepository;
import by.mrf1n.session_manager.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;

/**
 * Rest-контроллер api для доступа к БД из-за приложения
 */

@RestController
@RequestMapping(path = "/api")
public class ApiController {

    private final UserSessionRepository userSessionRepository;
    private final UserIpRepository userIpRepository;

    @Autowired
    public ApiController(UserSessionRepository userSessionRepository, UserIpRepository userIpRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userIpRepository = userIpRepository;
    }

    @GetMapping(value = "/userSessions")
    public List<UserSession> getAllUserSessions() {
        return userSessionRepository.findAll();
    }

    @PostMapping("/userSessions")
    public UserSession saveUserSession(@RequestBody UserSession newUserSession) {
        if (!userIpRepository.findById(newUserSession.getIpAddr()).isPresent()) {
            UserIp userIp = new UserIp();
            userIp.setIpAddr(newUserSession.getIpAddr());
            userIpRepository.save(userIp);
        }
        return userSessionRepository.save(newUserSession);

    }

    @GetMapping("/userSessions/{id}")
    public UserSession getUserSessionById(@PathVariable BigInteger id) {
        return userSessionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "UserSession Not Found"));
    }


    @PutMapping("/userSessions/{id}")
    public UserSession updateUserSession(@RequestBody UserSession newUser, @PathVariable BigInteger id) {
        return userSessionRepository.findById(id).map(userSession -> {
            userSession.setIpAddr(newUser.getIpAddr());
            userSession.setBannedBySession(newUser.isBannedBySession());
            return userSessionRepository.save(userSession);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "UserSession Not Found"));
    }

    @DeleteMapping("/userSessions/{id}")
    public void deleteUserSession(@PathVariable BigInteger id) {
        userSessionRepository.deleteById(id);

    }


    @GetMapping(value = "/userIps")
    public List<UserIp> getAllUserIps() {
        return userIpRepository.findAll();
    }

    @PostMapping("/userIps")
    public UserIp saveUserIp(@RequestBody UserIp newUserIp) {
        return userIpRepository.save(newUserIp);
    }

    @GetMapping("/userIps/{ip}")
    public UserIp getUserIpByIp(@PathVariable String ip) {
        return userIpRepository.findById(ip).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "UserIp Not Found"));
    }


    @PutMapping("/userIps/{id}")
    public UserIp updateUserIp(@RequestBody UserIp newUserIp, @PathVariable String id) {
        return userIpRepository.findById(id).map(userIp -> {
            userIp.setIpAddr(newUserIp.getIpAddr());
            userIp.setName(newUserIp.getName());
            userIp.setBannedByIp(newUserIp.isBannedByIp());
            return userIpRepository.save(userIp);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "UserIp Not Found"));
    }

    @DeleteMapping("/userIps/{ip}")
    public void deleteUserIp(@PathVariable String ip) {
        userIpRepository.deleteById(ip);

    }
}
