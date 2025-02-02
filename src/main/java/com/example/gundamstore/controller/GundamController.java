package com.example.gundamstore.controller;

import com.example.gundamstore.model.Gundam;
import com.example.gundamstore.service.GundamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gundams")
public class GundamController {

    private final GundamService gundamService;

    @Autowired
    public GundamController(GundamService gundamService) {
        this.gundamService = gundamService;
    }

    @GetMapping
    public List<Gundam> getAllGundams() {
        return gundamService.getAllGundams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gundam> getGundamById(@PathVariable String id) {
        Gundam gundam = gundamService.getGundamById(id);
        if (gundam != null) {
            return ResponseEntity.ok(gundam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Gundam createGundam(@RequestBody Gundam gundam) {
        return gundamService.createGundam(gundam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gundam> updateGundam(@PathVariable String id, @RequestBody Gundam gundamDetails) {
        Gundam updatedGundam = gundamService.updateGundam(id, gundamDetails);
        if (updatedGundam != null) {
            return ResponseEntity.ok(updatedGundam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGundam(@PathVariable String id) {
        boolean isDeleted = gundamService.deleteGundam(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
