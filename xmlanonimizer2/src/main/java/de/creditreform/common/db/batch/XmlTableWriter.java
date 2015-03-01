package de.creditreform.common.db.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XmlTableWriter extends AbstractBatchEntry {

	public XmlTableWriter(Connection conn, String schema) {
		super(conn, schema);
		// TODO Auto-generated constructor stub
	}

	public PreparedStatement getPreparedStatement() throws SQLException {
		//String sql = "update <schema>TBLXMLSTORAGE set TRANSFORMEDDOCUMENT = ? WHERE (cs_id = ?)";
		//String sql = "update <schema>TBLXMLSTORAGE set storagetype=null, compressed = null, xmldata = ? WHERE (cs_id = ?)";
		String sql = "update <schema>TBLXMLSTORAGE set storagetype=null, compressed = null, CREATIONDATE = (sysdate+2), xmldata = ? WHERE (cs_id = ?)";
		sql = sql.replaceAll("<schema>", schema);
		return conn.prepareStatement(sql);
	}


	/* (non-Javadoc)
	 * @see de.creditreform.n5.projects.mbbank.historicalagencies.persistence.importer.BatchExecutor.AbstractTableFiller#persistEntry(java.sql.PreparedStatement, de.creditreform.n5.projects.mbbank.historicalagencies.persistence.importer.BatchExecutor.BatchData)
	 */
	public boolean persistEntry(PreparedStatement stmt, BOEntryRow element) throws SQLException {

		int j = 0;
		stmt2setClob(++j, stmt, element.getAnonimizedXml());
		stmt.setLong(++j, element.getCsId());
		return true;
	}


}
