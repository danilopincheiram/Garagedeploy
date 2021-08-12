package ie.cct.Garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cct.Garage.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    public Service findByName(String name);
}
