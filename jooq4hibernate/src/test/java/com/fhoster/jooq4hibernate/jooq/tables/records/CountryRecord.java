/**
 * This class is generated by jOOQ
 */
package com.fhoster.jooq4hibernate.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.4"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CountryRecord extends org.jooq.impl.UpdatableRecordImpl<com.fhoster.jooq4hibernate.jooq.tables.records.CountryRecord> implements org.jooq.Record3<java.lang.Long, java.lang.String, java.lang.Long> {

	private static final long serialVersionUID = 485746310;

	/**
	 * Setter for <code>PUBLIC.COUNTRY.ID_COUNTRY</code>.
	 */
	public void setIdCountry(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.COUNTRY.ID_COUNTRY</code>.
	 */
	public java.lang.Long getIdCountry() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.COUNTRY.NAME</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.COUNTRY.NAME</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.COUNTRY.ID_CAPITAL_CITY</code>.
	 */
	public void setIdCapitalCity(java.lang.Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>PUBLIC.COUNTRY.ID_CAPITAL_CITY</code>.
	 */
	public java.lang.Long getIdCapitalCity() {
		return (java.lang.Long) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Long, java.lang.String, java.lang.Long> fieldsRow() {
		return (org.jooq.Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Long, java.lang.String, java.lang.Long> valuesRow() {
		return (org.jooq.Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY.ID_COUNTRY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field3() {
		return com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY.ID_CAPITAL_CITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getIdCountry();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value3() {
		return getIdCapitalCity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountryRecord value1(java.lang.Long value) {
		setIdCountry(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountryRecord value2(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountryRecord value3(java.lang.Long value) {
		setIdCapitalCity(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountryRecord values(java.lang.Long value1, java.lang.String value2, java.lang.Long value3) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CountryRecord
	 */
	public CountryRecord() {
		super(com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY);
	}

	/**
	 * Create a detached, initialised CountryRecord
	 */
	public CountryRecord(java.lang.Long idCountry, java.lang.String name, java.lang.Long idCapitalCity) {
		super(com.fhoster.jooq4hibernate.jooq.tables.Country.COUNTRY);

		setValue(0, idCountry);
		setValue(1, name);
		setValue(2, idCapitalCity);
	}
}
