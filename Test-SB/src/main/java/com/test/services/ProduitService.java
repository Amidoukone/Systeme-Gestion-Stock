package com.test.services;

import com.test.entities.Produit;
import com.test.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Optional<Produit> findById(int id) {

        return produitRepository.findById(id);
    }

    public Produit save(Produit produit)
    {
        System.out.println("Produit à sauvegarder: " + produit);
        return produitRepository.save(produit);
    }




    public void deleteById(int id) {

        produitRepository.deleteById(id);
    }
}
