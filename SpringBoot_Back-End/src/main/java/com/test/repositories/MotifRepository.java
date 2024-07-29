package com.test.repositories;

import com.test.entities.Motif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MotifRepository extends JpaRepository<Motif, Integer> {
    @Query("SELECT COUNT(*) FROM Motif")
    int countMotifs();
    Motif findByTitle(String title);
}
