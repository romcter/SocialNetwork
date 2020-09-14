package ua.com.prim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.prim.entity.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    boolean existsByEmail(String email);
    MyUser findByEmail(String emain);
    MyUser findByActivationCode(String activationCode);
    Iterable<MyUser> findAllByName(String name);
}
