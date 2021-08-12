package ie.cct.Garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cct.Garage.model.Staff;

public interface StaffRepository  extends JpaRepository<Staff, Integer> {
    public Staff findByName(String username);
}
