/**
 * This class is generated by jOOQ
 */
package com.fhoster.jooq4hibernate.jooq;

/**
 * A class modelling foreign key relationships between tables of the <code>PUBLIC</code> 
 * schema
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.4"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> SYS_PK_10092 = UniqueKeys0.SYS_PK_10092;
	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord> SYS_PK_10096 = UniqueKeys0.SYS_PK_10096;
	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.CompanyRecord> SYS_PK_10100 = UniqueKeys0.SYS_PK_10100;
	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord> SYS_PK_10106 = UniqueKeys0.SYS_PK_10106;
	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.EmployeeRecord> SYS_PK_10112 = UniqueKeys0.SYS_PK_10112;
	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord> SYS_PK_10121 = UniqueKeys0.SYS_PK_10121;
	public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.StateRecord> SYS_PK_10130 = UniqueKeys0.SYS_PK_10130;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord> FK_BV6UIUSPH2KO0KISD95B023XG = ForeignKeys0.FK_BV6UIUSPH2KO0KISD95B023XG;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord, com.fhoster.jooq4hibernate.jooq.tables.records.StateRecord> FK_5BJQKU7O9NJVGVKP8KT1YPA3W = ForeignKeys0.FK_5BJQKU7O9NJVGVKP8KT1YPA3W;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.CompanyRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_5X4QDOOR9UUTWBWJJDDNHRUXN = ForeignKeys0.FK_5X4QDOOR9UUTWBWJJDDNHRUXN;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord> FK_CVYAV791OGVBOFSWRJKD4CNNI = ForeignKeys0.FK_CVYAV791OGVBOFSWRJKD4CNNI;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.EmployeeRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_LOGKCVDAEE6XYWV5LSL9M1J2J = ForeignKeys0.FK_LOGKCVDAEE6XYWV5LSL9M1J2J;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.EmployeeRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_HM0G01YK4OF86MK55MGXNPYWD = ForeignKeys0.FK_HM0G01YK4OF86MK55MGXNPYWD;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CompanyRecord> FK_AYGTTOKBEMI0CHL7SI9HQ000 = ForeignKeys0.FK_AYGTTOKBEMI0CHL7SI9HQ000;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_KM9TXALYXQBMSPXT0P3UKINVM = ForeignKeys0.FK_KM9TXALYXQBMSPXT0P3UKINVM;
	public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.StateRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord> FK_MNLCLB4B80OSPHGASBDMDWOKG = ForeignKeys0.FK_MNLCLB4B80OSPHGASBDMDWOKG;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> SYS_PK_10092 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.Address.ADDRESS, com.fhoster.jooq4hibernate.jooq.tables.Address.ADDRESS.ID_ADDRESS);
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord> SYS_PK_10096 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.City.CITY, com.fhoster.jooq4hibernate.jooq.tables.City.CITY.ID_CITY);
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.CompanyRecord> SYS_PK_10100 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.Company.COMPANY, com.fhoster.jooq4hibernate.jooq.tables.Company.COMPANY.ID_COMPANY);
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord> SYS_PK_10106 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY, com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY.ID_COUNTRY);
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.EmployeeRecord> SYS_PK_10112 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.Employee.EMPLOYEE, com.fhoster.jooq4hibernate.jooq.tables.Employee.EMPLOYEE.ID_EMPLOYEE);
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord> SYS_PK_10121 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS, com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS.ID_COMPANY, com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS.ID_ADDRESS);
		public static final org.jooq.UniqueKey<com.fhoster.jooq4hibernate.jooq.tables.records.StateRecord> SYS_PK_10130 = createUniqueKey(com.fhoster.jooq4hibernate.jooq.tables.State.STATE, com.fhoster.jooq4hibernate.jooq.tables.State.STATE.ID_STATE);
	}

	private static class ForeignKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord> FK_BV6UIUSPH2KO0KISD95B023XG = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10096, com.fhoster.jooq4hibernate.jooq.tables.Address.ADDRESS, com.fhoster.jooq4hibernate.jooq.tables.Address.ADDRESS.ID_CITY);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord, com.fhoster.jooq4hibernate.jooq.tables.records.StateRecord> FK_5BJQKU7O9NJVGVKP8KT1YPA3W = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10130, com.fhoster.jooq4hibernate.jooq.tables.City.CITY, com.fhoster.jooq4hibernate.jooq.tables.City.CITY.ID_STATE);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.CompanyRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_5X4QDOOR9UUTWBWJJDDNHRUXN = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10092, com.fhoster.jooq4hibernate.jooq.tables.Company.COMPANY, com.fhoster.jooq4hibernate.jooq.tables.Company.COMPANY.ID_HEAD_OFFICE_ADDRESS);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CityRecord> FK_CVYAV791OGVBOFSWRJKD4CNNI = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10096, com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY, com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY.ID_CAPITAL_CITY);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.EmployeeRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_LOGKCVDAEE6XYWV5LSL9M1J2J = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10092, com.fhoster.jooq4hibernate.jooq.tables.Employee.EMPLOYEE, com.fhoster.jooq4hibernate.jooq.tables.Employee.EMPLOYEE.CURRENT_ADDRESS_ID);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.EmployeeRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_HM0G01YK4OF86MK55MGXNPYWD = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10092, com.fhoster.jooq4hibernate.jooq.tables.Employee.EMPLOYEE, com.fhoster.jooq4hibernate.jooq.tables.Employee.EMPLOYEE.PERMANENT_ADDRESS_ID);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CompanyRecord> FK_AYGTTOKBEMI0CHL7SI9HQ000 = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10100, com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS, com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS.ID_COMPANY);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord, com.fhoster.jooq4hibernate.jooq.tables.records.AddressRecord> FK_KM9TXALYXQBMSPXT0P3UKINVM = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10092, com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS, com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS.ID_ADDRESS);
		public static final org.jooq.ForeignKey<com.fhoster.jooq4hibernate.jooq.tables.records.StateRecord, com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord> FK_MNLCLB4B80OSPHGASBDMDWOKG = createForeignKey(com.fhoster.jooq4hibernate.jooq.Keys.SYS_PK_10106, com.fhoster.jooq4hibernate.jooq.tables.State.STATE, com.fhoster.jooq4hibernate.jooq.tables.State.STATE.ID_COUNTRY);
	}
}