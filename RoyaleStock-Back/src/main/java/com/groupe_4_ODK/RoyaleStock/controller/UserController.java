package com.groupe_4_ODK.RoyaleStock.controller;

import com.groupe_4_ODK.RoyaleStock.entite.Admin;
import com.groupe_4_ODK.RoyaleStock.entite.Manager;
import com.groupe_4_ODK.RoyaleStock.entite.Vendeur;
import com.groupe_4_ODK.RoyaleStock.service.AdminService;
import com.groupe_4_ODK.RoyaleStock.service.ManagerService;
import com.groupe_4_ODK.RoyaleStock.service.VendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
  @Autowired
  private ManagerService managerService;
  @Autowired
  private VendeurService vendeurService;

  @Autowired
  private AdminService adminService;

  @GetMapping("/admin/all")
  public List<Admin> allAdmin(){
    return  adminService.allAdmin();
  }
  @PostMapping("/admin/create")
  public Admin createAdmin(@RequestBody Admin admin){
    return adminService.createAdmin(admin);
  }

  @PutMapping("admin/update/{id}")
  public Admin updateAdmin(@PathVariable Integer id,
                           @RequestBody Admin admin) {
    return adminService.updateAdmin(id,admin);
  }

  //Vendeur

  @GetMapping("/vendeur/all")
  public List<Vendeur> allVendeur(){
    return  vendeurService.allVendeurs();
  }

  @PostMapping("/vendeur/create")
  public Vendeur createVendeur(@RequestBody Vendeur vendeur){
    return vendeurService.addVendeur(vendeur);
  }

  @PutMapping("vendeur/update/{id}")
  public Vendeur updateVendeur(@PathVariable Integer id, @RequestBody Vendeur vendeur) {

    return vendeurService.updateVendeur(id, vendeur);
  }

  @DeleteMapping("/vendeur/delete/{id}")
  public void deleteVendeur(@PathVariable long id){
    managerService.deleteManager(id);
  }


  //Manager
  @GetMapping("/manager/all")
  public List<Manager> allAManager(){
    return  managerService.allManager();
  }
  @PostMapping("manager/create")
  public Manager createManager(@RequestBody Manager manager){
    return managerService.createManager(manager);
  }

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
