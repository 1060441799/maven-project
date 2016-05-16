package com.newframe.core.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class DbDateType implements UserType {

	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		if (value == null) {
			return null;
		}
		if (!(value instanceof java.util.Date)) {
			throw new UnsupportedOperationException("Can not convert " + value.getClass());
		}
		return new Date(((Date) value).getTime());
	}

	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		if (!(arg0 instanceof java.util.Date)) {
			throw new UnsupportedOperationException("Can not convert " + arg0.getClass());
		}
		return (Date) arg0;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		if (x == y) {
			return true;
		}
		if (x == null) {
			return false;
		}
		return x.equals(y);
	}

	public int hashCode(Object x) throws HibernateException {
		// TODO Auto-generated method stub
		return (x == null) ? 0 : x.hashCode();
	}

	public boolean isMutable() {
		// TODO Auto-generated method stub
		return true;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		Timestamp value = rs.getTimestamp(names[0]);
		if (value == null) {
			return null;
		}
		return TimeUtil.fromDatabaseToUTC(value);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		if (value == null) {
			st.setNull(index, Types.TIMESTAMP);
		}
		if (!(value instanceof java.util.Date)) {
			throw new UnsupportedOperationException("Can not convert " + value.getClass());
		}
		Date saved = TimeUtil.fromUTCToDatabase((Date) value);
		st.setTimestamp(index, new Timestamp(saved.getTime()));
	}

	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	public Class returnedClass() {
		// TODO Auto-generated method stub
		return Date.class;
	}

	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return new int[] { Types.TIMESTAMP };
	}

}
