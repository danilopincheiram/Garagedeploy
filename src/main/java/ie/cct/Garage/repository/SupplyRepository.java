package ie.cct.Garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cct.Garage.model.Supply;

public interface SupplyRepository  extends JpaRepository<Supply, Integer> {
    public Supply findByName(String name);
}
