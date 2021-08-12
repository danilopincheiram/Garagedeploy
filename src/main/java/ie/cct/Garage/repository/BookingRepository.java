
package ie.cct.Garage.repository;

import ie.cct.Garage.model.Booking;
import ie.cct.Garage.model.Staff;
import ie.cct.Garage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;
// inherits the methods JPA repository                  //object and id   
public interface BookingRepository  extends JpaRepository<Booking, Integer> {
   
	public List<Booking> findByDate(Date date);

    public List<Booking> findByUser(User user);

    public List<Booking> findByStaffId(Integer id);
    
    public List<Booking> findByUser_id(int user_id);

	public List<Booking> findByDateBetween(Date start, Date end);

	public List<Booking> findByStaffAndDateBetween(Staff staff, Date dateStart, Date dateEnd);

}
