package com.test.services;

import com.test.entities.DetailSortie;
import com.test.repositories.DetailSortieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailSortieService {

    @Autowired
    private DetailSortieRepository detailSortieRepository;

    public List<DetailSortie> findAll() {
        return detailSortieRepository.findAll();
    }

    public Optional<DetailSortie> findById(int id) {
        return detailSortieRepository.findById(id);
    }

    public DetailSortie save(DetailSortie detailsSortie) {
        return detailSortieRepository.save(detailsSortie);
    }

    public void deleteById(int id) {
        detailSortieRepository.deleteById(id);
    }
}
