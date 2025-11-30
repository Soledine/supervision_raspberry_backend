package fr.cnam.serverMonitor.controller;


import fr.cnam.serverMonitor.entite.CpuMesure;
import fr.cnam.serverMonitor.entite.DTO.CpuMesureDto;
import fr.cnam.serverMonitor.entite.DTO.MemoryMesureDto;
import fr.cnam.serverMonitor.entite.MemoryMesure;
import fr.cnam.serverMonitor.repository.MesureCpuRepository;
import fr.cnam.serverMonitor.repository.MesureMemoryRepository;
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

    @Autowired
    private MesureMemoryRepository mesureMemoireRepository;

    @GetMapping("/mesuresCpu")
    public List<CpuMesureDto> getMesuresCpu(){
        return mesureCpuRepository.findAll().stream().map(CpuMesureDto::new).toList();
    }

    @GetMapping("/lastMesureCpu")
    public CpuMesureDto getLastMesureCpu(){
        CpuMesure mesure = mesureCpuRepository.findFirstByOrderByIdDesc();
        System.out.println("envoi de "+mesure);
        return new CpuMesureDto(mesure);
    }

    @GetMapping("/mesuresMemoire")
    public List<MemoryMesureDto> getMesuresMemoire(){
        return mesureMemoireRepository.findAll().stream().map(MemoryMesureDto::new).toList();
    }

    @GetMapping("/lastMesureMemoire")
    public MemoryMesureDto getLastMesureMemoire(){
        MemoryMesure mesure = mesureMemoireRepository.findFirstByOrderByIdDesc();
        System.out.println("envoi de "+mesure);
        return new MemoryMesureDto(mesure);
    }
}
