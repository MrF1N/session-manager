package by.mrf1n.session_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO для сущности сессии
 */

@Entity
@Table(name = "users")
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sequence_user")
    @TableGenerator(name="sequence_userSession", table = "id_gen", pkColumnName="gen_name", valueColumnName="gen_value", allocationSize=1000)
    @Column(name = "user_id")
    private BigInteger id;

    @Column(name = "is_banned_session")
    private boolean isBannedBySession = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ip_addr", insertable = false, updatable = false)
    private UserIp userIp;

    @Column(name = "ip_addr")
    private String ipAddr;


    public UserSession() {

    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public UserIp getUserIp() {
        return userIp;
    }

    public void setUserIp(UserIp userIp) {
        this.userIp = userIp;
    }

    public boolean isBannedBySession() {
        return isBannedBySession;
    }

    public void setBannedBySession(boolean bannedBySession) {
        isBannedBySession = bannedBySession;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", isBannedBySession=" + isBannedBySession +
                ", userIp=" + userIp +
                ", ipAddr='" + ipAddr + '\'' +
                '}';
    }
}
