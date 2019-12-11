package org.softuni.workman.repository;


import org.softuni.workman.domain.entities.Workman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkmanRepository extends JpaRepository<Workman, String> {

    Optional<Workman> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName, String lastName);

    @Query("select w from Workman w where upper(w.firstName) like concat('%', upper(?1), '%') or upper(w.lastName) like concat('%', upper(?2), '%') ")
    Optional<Workman> getByNames(String firstName, String lastName);
}
