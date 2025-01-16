package br.com.valkyriesystems.petspace.domain.ports.repositories;

import br.com.valkyriesystems.petspace.domain.models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(UUID id);
}