package com.fhoster.jooq4hibernate.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="state")
public class State {

	@Id
	@Column(name="id_state")
	private Long idState;
	private String name;
	@ManyToOne
	@JoinColumn(name="id_country")
	private Country country;
}
