package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Admin;
import com.groupe_4_ODK.RoyaleStock.entite.Manager;
import com.groupe_4_ODK.RoyaleStock.entite.Utilisateur;
import com.groupe_4_ODK.RoyaleStock.entite.Vendeur;
import com.groupe_4_ODK.RoyaleStock.service.AdminService;
import com.groupe_4_ODK.RoyaleStock.service.ManagerService;
import com.groupe_4_ODK.RoyaleStock.service.UtilisateurService;
import com.groupe_4_ODK.RoyaleStock.service.VendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {
  @Autowired
  private ManagerService managerService;
  @Autowired
  private VendeurService vendeurService;

  @Autowired
  private AdminService adminService;
  @Autowired
  private UtilisateurService utilisateurService;

  //List de tout les utilisateurs
  @GetMapping("list")
  public List<Utilisateur> listUtilisateur(){
    return utilisateurService.getAllUtilisateurs();
  }
  //Liste des user by Name
  @GetMapping("user")
  public Utilisateur getUtilisateurbyName(@RequestParam String name){
    return utilisateurService.findUtilisateurByName(name);
  }
  //Liste des Admin
  @GetMapping("/admin/all")
  public List<Admin> allAdmin(){
    return  adminService.allAdmin();
  }

  //Creer Admin
  @PostMapping("admin/create")
  public Admin createAdmin(@RequestBody Admin admin){
    return adminService.createAdmin(admin);
  }

  //Modifier Admin
  @PutMapping("admin/update/{id}")
  public Admin updateAdmin(@PathVariable Integer id,
                           @RequestBody Admin admin) {
    return adminService.updateAdmin(id,admin);
  }

  //Vendeur
  //List des vendeurs
  @GetMapping("/vendeur/all")
  public List<Vendeur> allVendeur(){
    return  vendeurService.allVendeurs();
  }

  //Creer Vendeur
  @PostMapping("/vendeur/create")
  public Vendeur createVendeur(@RequestBody Vendeur vendeur){
    return vendeurService.addVendeur(vendeur);
  }

  //Modifier Vendeur by Id
  @PutMapping("vendeur/update/{id}")
  public Vendeur updateVendeur(@PathVariable Integer id, @RequestBody Vendeur vendeur) {

    return vendeurService.updateVendeur(id, vendeur);
  }

  //Get Manager By Id
  @GetMapping("vendeurById/{id}")
  public Optional<Vendeur> getById( @PathVariable Integer id){
    return vendeurService.getVendeurById(id);
  }

  //Delette Vendeur
  @DeleteMapping("/vendeur/delete/{id}")
  public void deleteVendeur(@PathVariable long id){
    managerService.deleteManager(id);
  }

  //Manager
  //Liste des managers
  @GetMapping("/manager/all")
  public List<Manager> allAManager(){
    return  managerService.allManager();
  }

  //Creer manager
  @PostMapping("manager/create")
  public Manager createManager(@RequestBody Manager manager){
    return managerService.createManager(manager);
  }

  // Get Manager By Id
  @GetMapping("getMangerById/{id}")
  public Manager getManagerById(@PathVariable Long id){
    return managerService.getManagerById(id);
  }
  //Modifier Manager by Id
  @PutMapping("manager/update/{id}")
  public Manager updateManager(@PathVariable Long id,
                               @RequestBody Manager manager) {
    return managerService.updateManager(id,manager);
  }
  //delete Manager
  @DeleteMapping("manager/delete/{id}")
  public void deleteManager(@PathVariable long id){
    managerService.deleteManager(id);
  }
}
