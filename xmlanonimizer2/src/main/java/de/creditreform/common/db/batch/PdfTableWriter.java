package de.creditreform.common.db.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PdfTableWriter extends AbstractBatchEntry {


	/**
	 * Instantiates a new documents filler.
	 *
	 * @param conn the conn
	 */
	public PdfTableWriter(Connection conn, String schema) {
		super(conn, schema);
	}

	/* (non-Javadoc)
	 * @see de.creditreform.n5.projects.mbbank.historicalagencies.persistence.importer.BatchExecutor.AbstractTableFiller#getPreparedStatement()
	 */
	public PreparedStatement getPreparedStatement() throws SQLException {
		String sql = "update <schema>TBLPDFSTORAGE set content = ? where (cs_id = ?)";
		sql = sql.replaceAll("<schema>", schema);
		return conn.prepareStatement(sql);
	}



	/* (non-Javadoc)
	 * @see de.creditreform.n5.projects.mbbank.historicalagencies.persistence.importer.BatchExecutor.AbstractTableFiller#persistEntry(java.sql.PreparedStatement, de.creditreform.n5.projects.mbbank.historicalagencies.persistence.importer.BatchExecutor.BatchData)
	 */
	public boolean persistEntry(PreparedStatement stmt, BOEntryRow element) throws SQLException {
//		if (1==1) return false;
		if (element.getCsIdPdf() == null || element.getCsIdPdf().longValue() == 0L) return false;


		int j = 0;
		stmt2setBlob(++j, stmt, element.getPdf());				//CLOBDATA
		stmt2setLong(++j, stmt, element.getCsIdPdf());			//PARENT_ID

		return true;
	}

}
