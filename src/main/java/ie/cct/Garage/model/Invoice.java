package ie.cct.Garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoice", schema = "heroku_dbbdb28e0c9aeb8", catalog = "")
public class Invoice {

	@JsonIgnore
	@Id
	@GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
	private Integer id;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "serviceType")
    private String serviceType;

    @Column(name = "vehicle_make")
    private String vehicleMake;

    @Column(name = "vehicle_licence")
    private String vehicleLicence;

    @Column(name = "status")
    private String status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "invoice_supplies",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "supply_id")
    )
    private List<Supply> parts;

    @Column(name = "total")
    private double total;

    public Invoice(){
        super();
    }

    public Invoice(Booking booking, String email, String phoneNumber, String serviceType, List<Supply> parts, String vehicleMake, String vehicleLicence, String status, double total) {
        this.bookingId = booking.getId();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.serviceType = serviceType;
        this.parts = parts;
        this.vehicleMake = vehicleMake;
        this.vehicleLicence = vehicleLicence;
        this.status = status;
        this.parts = new ArrayList<>();
        this.parts.addAll(booking.getParts());
        this.total = total;
    }

    public Invoice(Booking booking) {
        super();
        this.bookingId = booking.getId();
        this.email = booking.getDetail().getEmail();
        this.phoneNumber = booking.getDetail().getPhoneNumber();
        this.serviceType = booking.getServiceType();
        this.status = booking.getStatus();
        this.vehicleMake = booking.getVehicle().getVehicleMake();
        this.vehicleLicence = booking.getVehicle().getVehicleLicence();
        this.parts = new ArrayList<>();
        this.parts.addAll(booking.getParts());
        this.total = booking.getCost();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking booking) {
        this.bookingId = booking.getId();
        this.email = booking.getDetail().getEmail();
        this.phoneNumber = booking.getDetail().getPhoneNumber();
        this.serviceType = booking.getServiceType();
        this.status = booking.getStatus();
        this.vehicleMake = booking.getVehicle().getVehicleMake();
        this.vehicleLicence = booking.getVehicle().getVehicleLicence();
        this.parts = new ArrayList<>();
        this.parts.addAll(booking.getParts());
        this.total = booking.getCost();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleLicence() {
        return vehicleLicence;
    }

    public void setVehicleLicence(String vehicleLicence) {
        this.vehicleLicence = vehicleLicence;
    }

    public List<Supply> getParts() {
        return parts;
    }

    public void setParts(List<Supply> parts) {
        this.parts = parts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
