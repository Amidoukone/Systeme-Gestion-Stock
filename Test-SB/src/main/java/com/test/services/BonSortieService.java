package com.test.services;

import com.test.entities.BonSortie;
import com.test.repositories.BonSortieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonSortieService {

    @Autowired
    private BonSortieRepository bonSortieRepository;

    public List<BonSortie> findAll() {
        return bonSortieRepository.findAll();
    }

    public Optional<BonSortie> findById(int id) {
        return bonSortieRepository.findById(id);
    }

    public BonSortie save(BonSortie bonSortie) {
        return bonSortieRepository.save(bonSortie);
    }

    public void deleteById(int id) {
        bonSortieRepository.deleteById(id);
    }
}
