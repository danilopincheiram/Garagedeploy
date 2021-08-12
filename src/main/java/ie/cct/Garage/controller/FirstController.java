package ie.cct.Garage.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ie.cct.Garage.model.*;
import ie.cct.Garage.repository.BookingRepository;
import ie.cct.Garage.repository.InvoiceRepository;
import ie.cct.Garage.repository.RosterRepository;
import ie.cct.Garage.repository.ServiceRepository;
import ie.cct.Garage.repository.StaffRepository;
import ie.cct.Garage.repository.UserDetailRepository;
import ie.cct.Garage.repository.UserRepository;
import ie.cct.Garage.repository.SupplyRepository;
import ie.cct.Garage.repository.VehicleRepository;
import ie.cct.Garage.exceptions.BadRequestException;
import ie.cct.Garage.exceptions.UnauthorizedException;
import ie.cct.Garage.util.JWTIssuer;
import io.jsonwebtoken.Claims;

import java.time.DayOfWeek;
import java.time.LocalDate;

import java.time.ZoneId;

@RestController
@CrossOrigin("*")
public class FirstController {

	// injects the dependency of the repository
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private UserDetailRepository detailRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private RosterRepository rosterRepository;

	@Autowired
	private SupplyRepository supplyRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	final int WORKER_CAPACITY = 4;


	// constructor
	public FirstController(ServiceRepository serviceRepository, SupplyRepository supplyRepository,
			StaffRepository staffRepository, UserRepository userRepository) {
		this.serviceRepository = serviceRepository;
		this.supplyRepository = supplyRepository;
		this.staffRepository = staffRepository;
		this.userRepository = userRepository;
		
		// create admin
		//userRepository.save(new User("admin", "Pass1234!"));

		// create the staff
		staffRepository.save(new Staff("Maeve"));
		staffRepository.save(new Staff("Santos"));
		staffRepository.save(new Staff("Mario"));
		staffRepository.save(new Staff("Danilo"));

		// create the supplies
		supplyRepository.save(new Supply("Air-fresheners", 2.0));
		supplyRepository.save(new Supply("Air Bag Diagnostic", 232.0));
		supplyRepository.save(new Supply("Air Conditioning", 299.0));
		supplyRepository.save(new Supply("Battery", 89.0));
		supplyRepository.save(new Supply("Brakes and Disks Repair", 322.0));
		supplyRepository.save(new Supply("Blinking Lights", 59.0));
		supplyRepository.save(new Supply("Brake Fluid", 39.0));
		supplyRepository.save(new Supply("Car Cover", 89.0));
		supplyRepository.save(new Supply("Car Keys", 322.0));
		supplyRepository.save(new Supply("Car Lights Renovation", 16.0));
		supplyRepository.save(new Supply("Carpets", 29.0));
		supplyRepository.save(new Supply("Dashboard Polish and Fragances", 15.0));
		supplyRepository.save(new Supply("Dry", 3.0));
		supplyRepository.save(new Supply("Engine Bay Cleaned", 282.0));
		supplyRepository.save(new Supply("Engine Oil", 69.0));
		supplyRepository.save(new Supply("Extinguisher", 89.0));
		supplyRepository.save(new Supply("Full Valet", 99.0));
		supplyRepository.save(new Supply("GPS", 59.0));
		supplyRepository.save(new Supply("Gearshift Cover", 14.0));
		supplyRepository.save(new Supply("Handwash", 12.0));
		supplyRepository.save(new Supply("Interior Lights", 38.0));
		supplyRepository.save(new Supply("Interior Vacummend", 15.0));
		supplyRepository.save(new Supply("Leather Car", 13.0));
		supplyRepository.save(new Supply("Luxury Valet", 129.0));
		supplyRepository.save(new Supply("Mirror", 240.0));
		supplyRepository.save(new Supply("Polarized", 229.0));
		supplyRepository.save(new Supply("Scratch Remover Ser", 29.0));
		supplyRepository.save(new Supply("Standar Valet", 60.0));
		supplyRepository.save(new Supply("Suspension Repair", 199.0));
		supplyRepository.save(new Supply("Shine Ceramic", 32.0));
		supplyRepository.save(new Supply("Seat Cover", 28.0));
		supplyRepository.save(new Supply("Seats and Carpets removed", 85.0));
		supplyRepository.save(new Supply("Tyre Dressing", 2.0));
		supplyRepository.save(new Supply("Tyre Repair", 20.0));
		supplyRepository.save(new Supply("Undercarriage wash", 6.0));
		supplyRepository.save(new Supply("Windows Cleaned", 3.0));
		supplyRepository.save(new Supply("Windshiel", 400.0));
		supplyRepository.save(new Supply("Wheel Balancing", 79.0));
		supplyRepository.save(new Supply("NTC Repair", 350.0));
		supplyRepository.save(new Supply("Clutch Replacement", 240.0));

		// create the services
		if (serviceRepository.findByName("Annual Service") == null) {
			serviceRepository.save(new Service("Annual Service", 230.90));
		}
		if (serviceRepository.findByName("Major Service") == null) {
			serviceRepository.save(new Service("Major Service", 783.32));
		}
		if (serviceRepository.findByName("Repair") == null) {
			serviceRepository.save(new Service("Repair", 360.00));
		}
		if (serviceRepository.findByName("Major Repair") == null) {
			serviceRepository.save(new Service("Major Repair", 232.23));
		}

	}

	/**
	 * CREATE AN ACCOUNT
	 * 
	 * @param username to create a user
	 * @param password to create a user and validation.
	 * @return
	 */
	@PostMapping("/users")
	// 204 means no content, it s success, but it does not have content
	@ResponseStatus(code = HttpStatus.OK) // response 200 instead
	public String registerUser(
			@RequestParam(required = true) String username, @RequestParam(required = true) String password) {

		if (password.length() < 10) {
			throw new BadRequestException("password should be at least 10 characters");
		}
		if (!containsAtLeastOneSymbol(password)) {
			throw new BadRequestException("password should cointain at leat 1 of the following symbols: @#+*/&()%$-:_<>=!");
		}
		if (userExist(username)) {
			throw new BadRequestException("User already exist");
		}

		User newUser = new User(username, password);

		// save user to database
		userRepository.save(newUser);

		return "user created successfully";
	}

	// method to check if the variable password contains at least one symbol
	private boolean containsAtLeastOneSymbol(String string) {

		String symbols = "@#+*/&()%$-:_<>=!";
		
		if (string != null) {
			for (int i = 0; i < symbols.length(); i++) {
				if (string.indexOf(symbols.charAt(i)) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	// check if the username is stored in the database.
	private boolean userExist(String user) {

		return userRepository.findByUsername(user) != null;
	}

	/**
	 * REGISTER STAFF
	 * 
	 * @param name  to register the mechanic
	 * @param token
	 * @return a string if it is added successfully
	 */
	@PostMapping("/staff")
	@ResponseStatus(code = HttpStatus.OK) // response 200 instead
	public String registerStaff(

			@RequestParam(name = "name", required = true) String name,
			@RequestHeader(name = "Authorization", required = true) String token) {

		if (staffExist(name)) {
			throw new BadRequestException("worker already exist");
		}
		staffRepository.save(new Staff(name));

		return "Staff created successfully";
	}

	// check if the name of the staff is stored in the database.
	private boolean staffExist(String name) {

		return staffRepository.findByName(name) != null;
	}

	/**
	 * GET STAFF
	 * 
	 * @param token
	 * @return the list of the mechanics
	 */
	@GetMapping("/staff")
	public List<Staff> getStaffs(@RequestHeader(name = "Authorization", required = true) String token) {

		return staffRepository.findAll();
	}

	/**
	 * LOGIN
	 * 
	 * @param username to check if the name is stored in db
	 * @param password to validate the user.
	 * @return
	 */
	@GetMapping("/login")
	public String login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UnauthorizedException("User is not registered yet");
		}
		if (user.getUsername().contentEquals(username) && user.getPassword().contentEquals(password)) {
			return JWTIssuer.createJWT(username, "Garage", username, 86400000);
		}

		throw new UnauthorizedException("The credentials are not valid, please try again");

	}

	/**
	 * USER ADDS BOOKING
	 * 
	 * @param token
	 * @param booking object that contains all the values of the atributes
	 * @return a string if it is stored successfully
	 * 
	 */
	@PostMapping("/booking")
	public String addBooking(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody(required = true) Booking booking) {

		// Token is divided in two parts. The first contains the Bearer and the second contains the token.
		Claims claims = JWTIssuer.decodeJWT(token.split(" ")[1]);
		// The "sub" is equal to username.
		String subClaim = claims.get("sub", String.class);

		// if user is found in database
		User user = userRepository.findByUsername(subClaim);

		if (user != null) {
            //instance of the calendar object
			Calendar cal = Calendar.getInstance();
			cal.setTime(booking.getDate());
			int hour1 = cal.get(Calendar.HOUR_OF_DAY);
			int bookingPerHour = 0;
			
			// marked on monday because when I marked on Sunday it still allow to store
			// bookings on Sundays but not on Saturdays, and now it does not allow to store bookings on Sundays
			if (DayOfWeek.of(cal.get(Calendar.DAY_OF_WEEK)) == DayOfWeek.MONDAY) {
				throw new BadRequestException("Bookings cannot be made on sundays");
			}
			// get number of bookings already for that day
			int noOfBooking = 0;
			for (Booking bk : bookingRepository.findAll()) {

				// counts double appointment if the service equals to "Major Repair"
				if (bk.getServiceType().startsWith("Major Repair") && bk.getDate().compareTo(booking.getDate()) == 0) {
					noOfBooking++;
					bookingPerHour++;
				}
				if (bk.getDate().compareTo(booking.getDate()) == 0) {
					noOfBooking++;

					cal.setTime(bk.getDate());
					int hour2 = cal.get(Calendar.HOUR_OF_DAY);

					if (hour1 == hour2) {
						bookingPerHour++;
					}
				}
			}
			if (bookingPerHour < 4) {
				// add booking only if number of bookings is less than available workers
				if (noOfBooking < staffRepository.count() * WORKER_CAPACITY) {
					Service service = serviceRepository.findByName(booking.getServiceType());
					if (service != null) {
						booking.setUser(user); // map booking to current user
						booking.setStatus("Booked"); // default value for booking
						booking.setCost(service.getPrice());
						// update database
						bookingRepository.save(booking);
						return "Booking created successfully";
					} else {
						throw new BadRequestException("Service doesn't exist");
					}

				} else {
					throw new BadRequestException("Bookings has reached limit for this day");
				}
			}
			throw new BadRequestException("Bookings has reached time limit for this day");

		} else {
			throw new UnauthorizedException("user not registered");
		}
	}
	


	/**
	 * GET BOOKINGS FOR A DAY
	 * 
	 * @param date  to get all the booking for that day
	 * @param token
	 * @return all the bookings for that specific day
	 */
	@GetMapping("/admin/bookings/day")
	public List<Booking> getBookingsForDay(
			@RequestParam("selectedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
			@RequestHeader(name = "Authorization", required = true) String token) {

		Date start = dateStart(date);
		Date end = dateEnd(date);
        
		// find the booking per day
		if (bookingRepository.findByDateBetween(start, end).isEmpty()) {
			throw new BadRequestException("No bookings made for this day");
		}
		return bookingRepository.findByDateBetween(start, end);
	}

	public Date dateEnd(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	public Date dateStart(Date date) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * GET BOOKINGS FOR A WEEK
	 * 
	 * @param week, receives the number of the week in a year
	 * @param token
	 * @return all the bookings in that week
	 */
	@GetMapping("/admin/bookings/week")
	public List<Booking> getBookingsForWeek(@RequestParam(name = "week", required = true) Integer week,
			// @RequestParam(name = "year", required = true) Integer year,
			@RequestHeader(name = "Authorization", required = true) String token) {

		if (week > 52) {
			throw new BadRequestException("Invalid Week");
		}

		ArrayList<Booking> bookingForWeek = new ArrayList<>();
		ArrayList<LocalDate> datesOfWeek = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();

		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			calendar.setWeekDate(LocalDate.now().getYear(), week, i);
			datesOfWeek.add(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}

		// add booking is date is among the week days
		for (Booking bk : bookingRepository.findAll()) {
			if (datesOfWeek.contains(bk.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {

				bookingForWeek.add(bk);
			}
			if (bookingForWeek.isEmpty()) {
				throw new BadRequestException("No bookings registered for this week");
			}
		}

		return bookingForWeek;
	}

	/**
	 * GET ALL BOOKINGS
	 * 
	 * @param token
	 * @return all the bookings
	 */
	@GetMapping("/admin/bookings")
	public List<Booking> getAllBookings(@RequestHeader(name = "Authorization", required = true) String token) {

		return bookingRepository.findAll();
	}

	/**
	 * ADD SUPPLIES
	 * 
	 * @param token
	 * @param supplies sends the name of the additional supply to get it from the db
	 * @param id of the bookings to add the supply
	 * @return a string if the supply is added successfully
	 */
	@PostMapping("/admin/bookings/addcost")
	public String addCostToBooking(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "supplies", required = true) String supplies,
			@RequestParam(name = "bookingId", required = true) Integer id) {

		// get a booking by id from database
		Booking booking = bookingRepository.findById(id).get();
		if (booking != null) {
			// get the supply by name from database
			Supply supply = supplyRepository.findByName(supplies);
			if (supply != null) {
				if (!booking.getParts().contains(supply)) {
					booking.addParts(supply);
					//update database
					bookingRepository.save(booking);
					return "Supply Added successfully";
				}
				throw new BadRequestException("Supply already added to the booking");
			}
		}
		throw new BadRequestException("Booking does not exist");
	}

	/**
	 * UPDATE THE STATUS OF THE BOOKING
	 * 
	 * @param token
	 * @param status to update the default value "Booked"
	 * @param id     of the booking to update the status of this booking.
	 * @return a string id the status if update successfully
	 */
	@PostMapping("/admin/bookings/update")
	public String updateStatus(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "status", required = true) String status,
			@RequestParam(name = "id", required = true) Integer id) {

		Booking booking = bookingRepository.findById(id).get();

		if (booking != null) {
			booking.setStatus(status);
			bookingRepository.save(booking);
			return "Status updated successfully";
		}
		throw new BadRequestException("Booking does not exist");
	}

	/**
	 * VIEW BOOKINGS PER USER
	 * 
	 * @param token
	 * @param username to see all the booking stored with this user
	 * @return a list that contains the bookings per user.
	 */
	@GetMapping("/user/bookings/{user}")
	public List<Booking> getBookingsPerUser(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "user") String username) {

		int user_id = 0;

		User user = userRepository.findByUsername(username);
		if (user != null) {
			user_id = user.getId();
		}
		ArrayList<Booking> bks = new ArrayList<>();
		bks.addAll(bookingRepository.findByUser_id(user_id));
		if (bks.isEmpty()) {
			throw new BadRequestException("User does not have bookings and details yet ");
		}
		return bks;
	}

	/**
	 * PRINT INVOICE
	 * 
	 * @param username to bring the invoices for that user
	 * @param token
	 * @return a list of invoices
	 */
	@GetMapping("/admin/bookings/invoice")
	public List<Invoice> printInvoice(@RequestParam(name = "username", required = true) String username,
			@RequestHeader(name = "Authorization", required = true) String token) { // admin authorization

		List<Invoice> invoices = new ArrayList<>();
		// add all the bookings found in database with that username
		List<Booking> bookings = bookingRepository.findByUser(userRepository.findByUsername(username));
		for (Booking bk : bookings) {
			if (bk.getStatus().contentEquals("Completed") || bk.getStatus().contentEquals("Collected") ) {
				// get the invoice from database
				Invoice invoice = invoiceRepository.findByBookingId(bk.getId());
				if (invoice == null) {
					invoice = invoiceRepository.save(new Invoice(bk));
				} else {
					// update the invoice
					invoice.setBookingId(bk);
					invoice = invoiceRepository.save(invoice);
				}
				invoices.add(invoice);
			}
		}
		return invoices;
	}

	/**
	 * ASSIGN BOOKING TO STAFF
	 * 
	 * @param token
	 * @param id        of the staff to assign the booking
	 * @param bookingId that will be assigned to the staff
	 * @return a string if the booking is assigned successfully to the staff
	 */
	@PostMapping("/admin/bookings/staff")
	public String assignTask(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(name = "staffId", required = true) Integer id,
			@RequestParam(name = "bookingId", required = true) Integer bookingId) {

		Staff staff = staffRepository.findById(id).get();
		Booking booking = bookingRepository.findById(bookingId).get();

		// Check if Staff hasn't reached his capacity
		List<Booking> bookings = bookingRepository.findByStaffAndDateBetween(staff, dateStart(booking.getDate()),
				dateEnd(booking.getDate()));

		if (rosterRepository.existsById(bookingId)) {
			throw new BadRequestException("Booking already assigned to the staff");
		}
		if (bookings.size() < WORKER_CAPACITY) {

			rosterRepository.save(new Roster(booking, staff, booking.getDate()));
			return "Tasks added to staff successfully";
		}
		throw new BadRequestException("Staff has reached his maximum capacity for this day");
	}

	/**
	 * GET ROSTER PER DAY
	 * 
	 * @param token
	 * @param date  to check in database the roster for that day
	 * @return all the bookings for that day plus the staff assigned to that booking
	 */
	@GetMapping("/admin/roster")
	public List<Roster> getRosterPerDay(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

		Date start = dateStart(date);
		Date end = dateEnd(date);

		if (rosterRepository.findByDateBetween(start, end).isEmpty()) {
			throw new BadRequestException("The roster for this day is not assigned yet");
		}
		return rosterRepository.findByDateBetween(start, end);

	}

}
