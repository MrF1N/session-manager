package by.mrf1n.session_manager.repository;

import by.mrf1n.session_manager.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, BigInteger> {

    Optional<UserSession> findById(BigInteger id);

    List<UserSession> findByIpAddr(String ipAddr);

    @Transactional
    void deleteById(BigInteger id);

}
