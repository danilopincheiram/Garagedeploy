package ie.cct.Garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cct.Garage.model.Vehicle;

public interface VehicleRepository  extends JpaRepository<Vehicle, Integer> {
}
