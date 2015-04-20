package com.fhoster.jooq4hibernate.test;

import com.fhoster.jooq4hibernate.jooq.tables.Address;
import com.fhoster.jooq4hibernate.jooq.tables.Employee;

public class WithoutAliasHibernateDSLContextTests extends AbstractHibernateDSLContextTest {
	private static final Employee e1 = Employee.EMPLOYEE;
	private static final Address a1 = Address.ADDRESS;

	public WithoutAliasHibernateDSLContextTests() {
		super(e1, a1);
	}
	
	
}
