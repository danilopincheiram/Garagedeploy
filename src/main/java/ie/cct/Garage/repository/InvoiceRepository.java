package ie.cct.Garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ie.cct.Garage.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

	Invoice findByBookingId(Integer id);

}
