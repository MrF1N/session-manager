package by.mrf1n.session_manager.controller;

import by.mrf1n.session_manager.model.UserIp;
import by.mrf1n.session_manager.model.UserSession;
import by.mrf1n.session_manager.repository.UserIpRepository;
import by.mrf1n.session_manager.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private final UserSessionRepository userSessionRepository;
    private final UserIpRepository userIpRepository;

    @Autowired
    public AdminController(UserSessionRepository userSessionRepository, UserIpRepository userIpRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userIpRepository = userIpRepository;
    }

    @RequestMapping("")
    public String getAdminPage(ModelMap model) {
        model.addAttribute("listUserSessions", userSessionRepository.findAll());
        model.addAttribute("listUserIps", userIpRepository.findAll());
        return "adminPage";
    }
//
//    @RequestMapping("/session/{id}")
//    public String updateSessionPage(ModelMap model, @PathVariable BigInteger id) {
//        model.addAttribute("userSession",
//            userSessionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
//                HttpStatus.NOT_FOUND, "User Session Not Found")));
//        return "userSession";
//    }
//
//    @RequestMapping("/session/edit")
//    public String editSession(@ModelAttribute("userSession") UserSession userSession) {
//        userSessionRepository.save(userSession);
//        return "redirect:/admin";
//    }

    @RequestMapping("/session/{id}/ban_session")
    public String banUserBySession(@PathVariable BigInteger id) {
        UserSession session = userSessionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Session Not Found"));
        session.setBannedBySession(!session.isBannedBySession());
        userSessionRepository.save(session);
        return "redirect:/admin";
    }

    @RequestMapping("/ip/{ipAddr}")
    public String updateIpPage(ModelMap model, @PathVariable String ipAddr) {
        model.addAttribute("userIp",
                userIpRepository.findById(ipAddr).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Ip Not Found")));
        return "userIp";
    }

    @RequestMapping("/ip/edit")
    public String editSession(@ModelAttribute("userSession") UserIp userIp) {
        userIpRepository.save(userIp);
        return "redirect:/admin";
    }

    @RequestMapping("/ip/{ipAddr}/ban_ip")
    public String banUserByIp(@PathVariable String ipAddr) {
        UserIp userIp = userIpRepository.findById(ipAddr).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Ip Not Found"));
        userIp.setBannedByIp(!userIp.isBannedByIp());
        userIpRepository.save(userIp);
        return "redirect:/admin";
    }

    @RequestMapping("/session/{id}/delete")
    public String deleteSession(@PathVariable BigInteger id) {
        userSessionRepository.deleteById(id);
        return "redirect:/admin";
    }

}
