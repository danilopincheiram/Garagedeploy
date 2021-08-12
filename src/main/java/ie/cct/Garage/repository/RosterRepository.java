package ie.cct.Garage.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cct.Garage.model.Booking;
import ie.cct.Garage.model.Roster;
import ie.cct.Garage.model.Staff;

public interface RosterRepository extends JpaRepository<Roster, Integer> {

	List<Roster> findByDateBetween(Date start, Date end);



}
