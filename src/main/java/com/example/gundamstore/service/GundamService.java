package com.example.gundamstore.service;

import com.example.gundamstore.model.Gundam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GundamService {

    private List<Gundam> gundams = new ArrayList<>();

    public List<Gundam> getAllGundams() {
        return gundams;
    }

    public Gundam getGundamById(String id) {
        Optional<Gundam> gundam = gundams.stream().filter(g -> g.getId().equals(id)).findFirst();
        return gundam.orElse(null);
    }

    public Gundam createGundam(Gundam gundam) {
        gundams.add(gundam);
        return gundam;
    }

    public Gundam updateGundam(String id, Gundam gundamDetails) {
        Gundam gundam = getGundamById(id);
        if (gundam != null) {
            gundam.setName(gundamDetails.getName());
            gundam.setSeries(gundamDetails.getSeries());
            gundam.setPrice(gundamDetails.getPrice());
            gundam.setStock(gundamDetails.getStock());
            return gundam;
        }
        return null;
    }

    public boolean deleteGundam(String id) {
        return gundams.removeIf(gundam -> gundam.getId().equals(id));
    }
}
