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
public class OfficeAddressRecord extends org.jooq.impl.UpdatableRecordImpl<com.fhoster.jooq4hibernate.jooq.tables.records.OfficeAddressRecord> implements org.jooq.Record2<java.lang.Long, java.lang.Long> {

	private static final long serialVersionUID = -37032508;

	/**
	 * Setter for <code>PUBLIC.OFFICE_ADDRESS.ID_COMPANY</code>.
	 */
	public void setIdCompany(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.OFFICE_ADDRESS.ID_COMPANY</code>.
	 */
	public java.lang.Long getIdCompany() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.OFFICE_ADDRESS.ID_ADDRESS</code>.
	 */
	public void setIdAddress(java.lang.Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.OFFICE_ADDRESS.ID_ADDRESS</code>.
	 */
	public java.lang.Long getIdAddress() {
		return (java.lang.Long) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record2<java.lang.Long, java.lang.Long> key() {
		return (org.jooq.Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Long, java.lang.Long> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Long, java.lang.Long> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS.ID_COMPANY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field2() {
		return com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS.ID_ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getIdCompany();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value2() {
		return getIdAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfficeAddressRecord value1(java.lang.Long value) {
		setIdCompany(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfficeAddressRecord value2(java.lang.Long value) {
		setIdAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OfficeAddressRecord values(java.lang.Long value1, java.lang.Long value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OfficeAddressRecord
	 */
	public OfficeAddressRecord() {
		super(com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS);
	}

	/**
	 * Create a detached, initialised OfficeAddressRecord
	 */
	public OfficeAddressRecord(java.lang.Long idCompany, java.lang.Long idAddress) {
		super(com.fhoster.jooq4hibernate.jooq.tables.OfficeAddress.OFFICE_ADDRESS);

		setValue(0, idCompany);
		setValue(1, idAddress);
	}
}
