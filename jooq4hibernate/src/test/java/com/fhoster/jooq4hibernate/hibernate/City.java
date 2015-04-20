package com.fhoster.jooq4hibernate.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {

	@Id
	@Column(name="id_city")
	private Long idCity;
	private String name;
	@ManyToOne
	@JoinColumn(name="id_state")
	private State state;
	
	

}