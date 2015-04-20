package com.fhoster.jooq4hibernate.hibernate;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {

	@Id
	@Column(name="id_company")
	private Long idCompany;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="id_head_office_address")
	private Address headOfficeAddress;
	
	@ManyToMany
	@JoinTable(name = "office_address", joinColumns = { 
			@JoinColumn(name = "id_company", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "id_address", nullable = false, updatable = false) })
	private Set<Address> officeAddresses;
}
