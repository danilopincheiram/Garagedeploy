package ie.cct.Garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "vehicle", schema = "heroku_dbbdb28e0c9aeb8", catalog = "")
public class Vehicle {
    
	@JsonIgnore
    @Id
	@GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
    private Integer id;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_make")
    private String vehicleMake;

    @Column(name = "vehicle_licence")
    private String vehicleLicence;

    @Column(name = "vehicle_engine_type")
    private String vehicleEngineType;

    public Vehicle() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Vehicle(String mainService, String username, String phoneNumber, String vehicleType, String vehicleMake, String vehicleLicence,
                   String vehicleEngineType, String bookingRequired) {
        super();
        this.vehicleType = vehicleType;
        this.vehicleMake = vehicleMake;
        this.vehicleLicence = vehicleLicence;
        this.vehicleEngineType = vehicleEngineType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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

    public void setVehicleLicense(String vehicleLicence) {
        this.vehicleLicence = vehicleLicence;
    }

    public String getVehicleEngineType() {
        return vehicleEngineType;
    }

    public void setVehicleEngineType(String vehicleEngineType) {
        this.vehicleEngineType = vehicleEngineType;
    }

}

