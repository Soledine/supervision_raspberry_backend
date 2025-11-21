package fr.cnam.serverMonitor.controller;


import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MesureController {

    @Autowired
    private MesureCpuRepository mesureCpuRepository;

    @GetMapping("/mesuresCpu")
    public List<CpuMesure> getMesures(){
        return mesureCpuRepository.findAll();
    }
}
