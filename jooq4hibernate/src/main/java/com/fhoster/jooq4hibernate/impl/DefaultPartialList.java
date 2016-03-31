package com.fhoster.jooq4hibernate.impl;

import java.math.BigInteger;
import java.util.List;

import com.fhoster.jooq4hibernate.PartialList;

public class DefaultPartialList<T> implements PartialList<T>{
	private final List<T> partialList;
	private final BigInteger totalSize;

	DefaultPartialList(List<T> partialList, BigInteger count) {
		this.partialList = partialList;
		this.totalSize = count;
	}
	
	protected static <T> PartialList<T> newInstance(List<T> partialList, BigInteger totalSize){
		return new DefaultPartialList<T>(partialList, totalSize);
	}

	public List<T> partialList() {
		return partialList;
	}

	public BigInteger totalSize() {
		return totalSize;
	}
}
