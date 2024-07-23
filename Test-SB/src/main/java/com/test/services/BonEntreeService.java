package com.test.services;

import com.test.entities.BonEntree;
import com.test.repositories.BonEntreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonEntreeService {

    @Autowired
    private BonEntreeRepository bonEntreeRepository;

    public List<BonEntree> findAll() {
        return bonEntreeRepository.findAll();
    }

    public Optional<BonEntree> findById(int id) {
        return bonEntreeRepository.findById(id);
    }

    public BonEntree save(BonEntree bonEntree) {
        return bonEntreeRepository.save(bonEntree);
    }

    public void deleteById(int id) {
        bonEntreeRepository.deleteById(id);
    }
}
