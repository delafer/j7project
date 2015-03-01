package de.creditreform.common.db.batch;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class BatchExecutor.
 * @author tavrovsa
 */
public class BatchExecutor {



	/** The root filler. */
	private AbstractBatchEntry rootTableWriter;

	/** The java.sql.Connection instance */
	private Connection conn;

	/** The db schema. */
	private String dbSchema;


	/**
	 * Instantiates a new batch executor.
	 *
	 * @param conn the java.sql.Connection instance
	 * @param dbSchema the database schema
	 */
	public BatchExecutor(Connection conn, String dbSchema) {

		this.conn = conn;
		this.dbSchema = getCorrectSchema(dbSchema);
		rootTableWriter = new XmlTableWriter(conn, this.dbSchema);
		rootTableWriter.setParent(new PdfTableWriter(conn, this.dbSchema));
	}

	/**
	 * Gets the normalized schema in a right format .
	 *
	 * @param dbSchema the db schema
	 * @return the correct schema
	 */
	private String getCorrectSchema(String dbSchema) {
		String schema =  notNull(dbSchema);
		return schema.endsWith(".") || schema.isEmpty() ? schema  : schema + '.';
	}

	/**
	 * Adds the entity.
	 *
	 * @param data the data
	 * @throws SQLException the sQL exception
	 */
	public void addEntity(BOEntryRow data) throws SQLException {
		rootTableWriter.addEntity(data);
	}


	/**
	 * Flush.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void flush() throws SQLException {
		rootTableWriter.persistEntries();
		conn.commit();

	}

	/**
	 * Close.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void close() throws SQLException {
		flush();
		conn.close();
	}

	/**
	 * Not null.
	 *
	 * @param value the value
	 * @return the string
	 */
	protected String notNull(String value) {
		return value != null ? value : "";
	}



}
