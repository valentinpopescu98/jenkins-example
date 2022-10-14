package com.valentinpopescu98.testing.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student s SET s.name=?2 WHERE s.id=?1")
    void updateById(Long id, String newName);
}
