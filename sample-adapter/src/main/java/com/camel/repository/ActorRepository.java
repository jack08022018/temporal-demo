package com.camel.repository;

import com.camel.entity.ActorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query(value = """
            SELECT a
            FROM ActorEntity a
            WHERE a.actorId = :id
        """)
    ActorEntity findLockOptimistic(@Param("id") int id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
            SELECT a
            FROM ActorEntity a
            WHERE a.actorId = :id
        """)
    ActorEntity findLockPessimistic(@Param("id") int id);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({@QueryHint(name = AvailableSettings.JPA_LOCK_TIMEOUT, value = LockOptions.SKIP_LOCKED + "")})
//    @Query(value = "SELECT a FROM ActorEntity a WHERE lastName = 'GUINESS'")
//    @Query(value = "SELECT * FROM actor WHERE last_name = 'GUINESS' LIMIT 2 for UPDATE SKIP LOCKED", nativeQuery = true)
    @Query(value = "SELECT * FROM actor WHERE last_name = 'GUINESS' ", nativeQuery = true)
    List<ActorEntity> findTop2ByLastName();

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({@QueryHint(name = AvailableSettings.JPA_LOCK_TIMEOUT, value = "5000")})
//    Optional<ActorEntity> findById(Integer actorId);

//    @EntityGraph(attributePaths = {"films"})
    Optional<ActorEntity> findById(Integer actorId);

}
