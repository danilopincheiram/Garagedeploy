package ie.cct.Garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "staff", schema = "heroku_92a83c9c6123377", catalog = "")
public class Staff {
	
	//@JsonIgnore
	@Id
	@GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
	private Integer id;

	@Column(name = "name")
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
	private List<Booking> task;
	
	
	public Staff() {
		super();
	
	}
	
	public Staff(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
