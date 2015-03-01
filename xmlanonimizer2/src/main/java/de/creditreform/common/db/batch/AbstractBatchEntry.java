package de.creditreform.common.db.batch;

import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractBatchEntry {

	public static final transient int batchSize = 512;

	/** The conn. */
	protected Connection conn;

	/** The fifo. */
	protected Queue<BOEntryRow> fifo =new LinkedList<BOEntryRow>();

	/** The parent. */
	protected AbstractBatchEntry parent;

	protected String schema;

	/**
	 * Instantiates a new abstract table filler.
	 *
	 * @param conn the conn
	 */
	public AbstractBatchEntry(Connection conn, String schema) {
		this.conn = conn;
		this.schema = schema;
	}

	/**
	 * Persist entry.
	 *
	 * @param stmt the stmt
	 * @param element the element
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 */
	public abstract boolean persistEntry(PreparedStatement stmt, BOEntryRow element)throws SQLException;

	/**
	 * Gets the prepared statement.
	 *
	 * @return the prepared statement
	 * @throws SQLException the sQL exception
	 */
	public abstract PreparedStatement getPreparedStatement() throws SQLException;

	/**
	 * Persist entries.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void persistEntries() throws SQLException {

		if (fifo.isEmpty()) {
			return ;
		}

		BOEntryRow nextRow;
		PreparedStatement stmt = getPreparedStatement();

		while ((nextRow = fifo.poll())!=null) {
			if (persistEntry(stmt, nextRow)) {
				stmt.addBatch();
				if (parent != null) parent.fifo.add(nextRow);
			}
		}

		stmt.executeBatch();
		stmt.close();
		conn.commit();

		if (parent != null) {
			parent.persistEntries();
		} else {
			conn.commit();
		}
	}


	/**
	 * Adds the entity.
	 *
	 * @param data the data
	 * @throws SQLException the sQL exception
	 */
	public void addEntity(BOEntryRow data) throws SQLException {
		this.fifo.add(data);
		if (fifo.size() >= batchSize) {
			persistEntries();
		}

	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public AbstractBatchEntry getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the parent to set
	 */
	public void setParent(AbstractBatchEntry parent) {
		this.parent = parent;
	}





	public Date asDate(Calendar inputDate) {
		if (null == inputDate) return null;
		return new java.sql.Date(inputDate.getTimeInMillis());
	}

	public void stmt2setBlob(int at, PreparedStatement stmt, String value) throws SQLException {
		if (null == value)
			stmt.setNull(at, java.sql.Types.BLOB);
		else {
			byte[] arr = value.getBytes();
			stmt.setBytes(at, arr);
			};
		}

	public void stmt2setBlob(int at, PreparedStatement stmt, byte[] bytes) throws SQLException {
		if (null == bytes)
			stmt.setNull(at, java.sql.Types.BLOB);
		else {
			stmt.setBytes(at, bytes);
			};
		}


	public void stmt2setClob(int at, PreparedStatement stmt, String value) throws SQLException {
		if (null == value)
			stmt.setNull(at, java.sql.Types.CLOB);
		else {
//			byte[] arr = value.getBytes();
//			stmt.setBytes(at, arr);
			stmt.setString(at, value);
			};
		}

	public void stmt2setDouble(int at, PreparedStatement stmt, Double value) throws SQLException {
		if (null == value)
			stmt.setNull(at, java.sql.Types.DOUBLE);
		else
			stmt.setDouble(at, value.doubleValue());

	}

	public void stmt2setInt(int at, PreparedStatement stmt, Integer value) throws SQLException {
		if (null == value)
			stmt.setNull(at, java.sql.Types.INTEGER);
		else
			stmt.setInt(at, value.intValue());
	}

	public void stmt2setLong(int at, PreparedStatement stmt, Long value) throws SQLException {
		if (null == value)
			stmt.setNull(at, java.sql.Types.BIGINT);
		else
			stmt.setLong(at, value.longValue());
	}

	public Time asTime(Time inputTime) {
		if (null == inputTime) return null;
		return new java.sql.Time(inputTime.getTime());
	}

	public Date asDate(java.util.Date inputDate) {
		if (null == inputDate) return null;
		return new java.sql.Date(inputDate.getTime());
	}

}

