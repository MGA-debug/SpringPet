package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static PetModel petModel = PetModel.getInstance();
    public static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(newId.getAndIncrement(), pet);
        if (petModel.getAll().size() == 1)
            return "Congratulations, you have created your first pet";
        else
            return "You have created a pet";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public String getPet(@RequestBody Map<String, Integer> id) {
        if (!PetModel.getInstance().getAll().containsKey(id.get("id"))) {
            return "Sorry, there is no pet with this id";
        } else
            return PetModel.getInstance().getFromList(id.get("id")).toString();
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public Map<Integer, Pet> deletePet(@RequestBody Map<String, Integer> id) {
        PetModel.getInstance().delete(id.get("id"));
        return petModel.getAll();
    }

    @PutMapping(value = "/updatePet", consumes = "application/json", produces = "application/json")
    public Pet updateInfo(@RequestBody Map<String, String> pet) {
        return petModel.update(pet.get("id"), pet.get("name"), pet.get("type"), pet.get("age"));
    }
}
