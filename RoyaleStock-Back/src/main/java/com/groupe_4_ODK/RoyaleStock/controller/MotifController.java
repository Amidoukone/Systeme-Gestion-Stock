package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Motif;
import com.groupe_4_ODK.RoyaleStock.service.MotifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/motif")
public class MotifController {
@Autowired
private MotifService motifService;
  @PostMapping("/create")
  public Motif createMotif(@RequestBody Motif motif){
    return motifService.createMotif(motif);
  }
  @GetMapping("/id")
  public Optional<Motif> getMotifById(int id){
    return motifService.getMotifById(id);
  }
  //List des motifs
  @PostMapping("/list")
  public List<Motif> listMotif(){
    return motifService.getAllMotifs();
  }
  //modifier Motif
  @PutMapping("/update/{id}")
  public Motif updateMotif(@PathVariable int id, @RequestBody Motif motif){
    return motifService.updateMotif(motif,id);
  }
  //Supprimer motif
  @DeleteMapping("/delete/{id}")
  public void deleteMotif(@PathVariable int id){
    motifService.deleteMotif(id);
  }

  //Nombre de Motif
  @GetMapping("/motifNombre")
  public int getMotifNombre() {
    return motifService.getNombreMotif();
  }

}
