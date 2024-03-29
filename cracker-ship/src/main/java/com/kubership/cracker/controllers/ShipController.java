package com.kubership.cracker.controllers;

import com.kubership.cracker.model.Ship;
import com.kubership.cracker.repository.ShipRepository;
import com.kubership.cracker.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
@CrossOrigin("http://localhost:5173")
public class ShipController {

    @Autowired
    private ShipService shipService;
    @PostMapping()
    public ResponseEntity<Ship> insertShip(@RequestBody Ship shipBody){
        if(shipBody==null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Ship ship=shipService.insertShip(shipBody);

        if(ship==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Ship>> getShips(@RequestParam(value = "ownerid", required = true) int ownerNr){
        if(ownerNr<0)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Ship> ships=shipService.getShipsByOwner(ownerNr);

        return ResponseEntity.ok(ships);
    }

    @GetMapping("/{shipnr}")
    public ResponseEntity<Ship> getShip(@PathVariable("shipnr") int shipnr){
        if(shipnr<0)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship=shipService.getShip(shipnr);
        if(ship==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<Ship> updateShip(@RequestBody Ship shipBody){
        if(shipBody==null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Ship updatedShip=shipService.updateShip(shipBody);

        if(updatedShip==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(updatedShip, HttpStatus.OK);
    }

    @DeleteMapping("/{shipnr}")
    public ResponseEntity deleteShip(@PathVariable("shipnr") int shipnr){
        if(shipnr<0)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean success=shipService.deleteShip(shipnr);
        if(!success)return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
