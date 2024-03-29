package com.kubership.cracker.services;

import com.kubership.cracker.model.*;
import com.kubership.cracker.repository.ShipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ShipService {

    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private CrewMemberService crewMemberService;

    public Ship insertShip(Ship ship){
        if(ship==null)return null;

        Ship shipExists=shipRepository.findShipByShipnr(ship.getShipnr());
        if(shipExists!=null)return null;

        return shipRepository.save(ship);
    }

    public List<Ship> getShipsByOwner(int owner){
        if(owner<0)return null;

        return shipRepository.findShipsByOwner(owner);
    }

    public Ship getShip(int shipnr){
        if(shipnr<0)return null;
        return shipRepository.findShipByShipnr(shipnr);
    }

    public Ship updateShip(Ship ship){
        Ship shipExists=shipRepository.findShipByShipnr(ship.getShipnr());

        if(shipExists==null)return null;

        shipExists.setName(ship.getName());
        shipExists.setType(ship.getType());
        shipExists.setCurrentvalue(ship.getCurrentvalue());
        shipExists.setOwner(ship.getOwner());
        shipExists.setYear(ship.getYear());
        shipExists.setImage(ship.getImage());

        return shipRepository.save(shipExists);
    }

    public boolean deleteShip(int shipnr){
        if(shipnr<0)return false;

        Ship shipExists=shipRepository.findShipByShipnr(shipnr);
        if(shipExists==null)return false;

        for(Ship_Shipment shipment: shipExists.getShipments()){
            shipmentService.deleteShipment(shipment.getShipment().getShipmentid());
        }

        for(Ship_CrewMember crewMember: shipExists.getCrewmembers()){
            crewMemberService.deleteCrewMember(crewMember.getCrewmember().getCrewmemberid());
        }

        for(Ship_Maintenance maintenance: shipExists.getMaintenances()){
            maintenanceService.deleteMaintenance(maintenance.getId());
        }

        shipRepository.delete(shipExists);
        return true;
    }
}
