package com.test.services;

import com.test.entities.Entrepot;
import com.test.repositories.EntrepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepotService {

    @Autowired
    private EntrepotRepository entrepotRepository;

    public List<Entrepot> findAll() {
        return entrepotRepository.findAll();
    }

    public Optional<Entrepot> findById(int id) {
        return entrepotRepository.findById(id);
    }

    public Entrepot save(Entrepot entrepot) {
        return entrepotRepository.save(entrepot);
    }

    public void deleteById(int id) {
        entrepotRepository.deleteById(id);
    }
}
