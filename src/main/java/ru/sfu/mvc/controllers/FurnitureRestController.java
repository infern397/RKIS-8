package ru.sfu.mvc.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sfu.mvc.models.Furniture;
import ru.sfu.mvc.services.FurnitureService;

import java.util.List;


@RestController
@RequestMapping("/api/")
public class FurnitureRestController {

    private final FurnitureService furnitureService;

    @Autowired
    public FurnitureRestController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping
    public ResponseEntity<List<Furniture>> getAll(@RequestParam(name = "price", required = false) Float price) {
        if (price != null) {
            return new ResponseEntity<>(furnitureService.filterByPrice(price), HttpStatus.OK);
        }
        return new ResponseEntity<>(furnitureService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Furniture> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(furnitureService.findOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Furniture furniture) {
        furnitureService.save(furniture);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Furniture furniture, @PathVariable("id") int id) {
        furnitureService.update(id, furniture);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        furnitureService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
