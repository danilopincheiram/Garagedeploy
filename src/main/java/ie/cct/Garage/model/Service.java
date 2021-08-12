package ie.cct.Garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "services", schema = "heroku_dbbdb28e0c9aeb8", catalog = "")
public class Service {
  
	@JsonIgnore
    @Id
    @GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price")
    private Double price;

    public Service(){
        super();
        // TODO Auto-generated constructor stub
    }

    public Service(String name, Double price) {
        super();
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}