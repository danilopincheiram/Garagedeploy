package ie.cct.Garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


//constructor
@Entity
@Table(name = "user", schema = "heroku_92a83c9c6123377", catalog = "")
public class User {
	
	@JsonIgnore
	@Id
	@GeneratedValue( generator = "increment" )
    @GenericGenerator( name = "increment", strategy = "increment" )
	private Integer id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@JsonIgnore
	@Column(name = "password", nullable = false, length = 50)
	private String password;

	//constructor
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}