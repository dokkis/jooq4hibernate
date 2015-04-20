package com.fhoster.jooq4hibernate.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="country")
public class Country {

	@Id
	@Column(name="id_country")
	private Long idCountry;
	private String name;
	@OneToOne
	@JoinColumn(name="id_capital_city")
	private City capitalCity;
	
}
