package com.fhoster.jooq4hibernate.test;

import com.fhoster.jooq4hibernate.jooq.tables.Address;
import com.fhoster.jooq4hibernate.jooq.tables.Employee;

public class WithAliasHibernateDSLContextTests extends AbstractHibernateDSLContextTest {
	private static final Employee e1 = Employee.EMPLOYEE.as("e1");
	private static final Address a1 = Address.ADDRESS.as("a1");

	public WithAliasHibernateDSLContextTests() {
		super(e1, a1);
	}
	
	
}
