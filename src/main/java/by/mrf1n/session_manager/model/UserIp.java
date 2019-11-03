package by.mrf1n.session_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ips")
public class UserIp implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ip_addr")
    private String ipAddr;

    @Column(name = "ip_name")
    private String name;

    @Column(name = "is_banned_ip")
    private boolean isBannedByIp = false;

    @OneToMany(mappedBy = "userIp", cascade = CascadeType.ALL)
    private List<UserSession> userSessions;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public boolean isBannedByIp() {
        return isBannedByIp;
    }

    public void setBannedByIp(boolean bannedByIp) {
        isBannedByIp = bannedByIp;
    }

    public List<UserSession> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(List<UserSession> userSessions) {
        this.userSessions = userSessions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserIp{" +
                "ipAddr='" + ipAddr + '\'' +
                ", name='" + name + '\'' +
                ", isBannedByIp=" + isBannedByIp +
                ", userSessions=" + userSessions +
                '}';
    }
}
