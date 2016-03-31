package com.fhoster.jooq4hibernate;

import java.math.BigInteger;
import java.util.List;

public interface PartialList<T> {
	public List<T> partialList();

	public BigInteger totalSize();
}
