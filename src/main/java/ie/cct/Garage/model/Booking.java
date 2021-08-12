
package ie.cct.Garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings", schema = "heroku_92a83c9c6123377", catalog = "")
public class Booking {
	
	//@JsonIgnore
	@Id
	@GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "roster",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id")
    )
    private Staff staff;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private UserDetail detail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "date")
    private Date date;

    @Column(name = "comment")
    private String comment;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "booking_supplies",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "supply_id")
    )
    private List<Supply> parts;

    @Column(name = "cost")
    private double cost;

    @Column(name = "status")
    private String status;

    public Booking(){
        super();
        this.parts = new ArrayList<>();   // default constructor
    }

    public Booking(UserDetail detail, Vehicle vehicle, String serviceType, Date date, String comment, double cost) {
        this.detail = detail;
        this.vehicle = vehicle;
        this.serviceType = serviceType;
        this.date = date;
        this.comment = comment;
        this.cost = cost;
    }

    public Booking(User user, UserDetail detail, Vehicle vehicle, String serviceType, Date date, String comment) {
        detail.setUser(user);
        this.user = user;
        this.detail = detail;
        this.vehicle = vehicle;
        this.serviceType = serviceType;
        this.date = date;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Supply> getParts() {
        return parts;
    }

    public void setParts(List<Supply> parts) {
        for (Supply part : parts){
            addCost(part.getPrice());
        }
        this.parts = parts;
    }

    public void addParts(Supply part){
        addCost(part.getPrice());
        this.parts.add(part);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.detail.setUser(user);
        this.user = user;
    }

    public UserDetail getDetail() {
        return detail;
    }

    public void setDetail(UserDetail detail) {
        detail.setUser(user);
        this.detail = detail;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void addCost(double cost){
        this.cost += cost;
    }

}



