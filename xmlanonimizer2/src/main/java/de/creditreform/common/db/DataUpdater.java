package de.creditreform.common.db;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.*;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.creditreform.common.cli.CliProgress;
import de.creditreform.common.db.batch.BOEntryRow;
import de.creditreform.common.db.batch.BatchExecutor;
import de.creditreform.common.helpers.Args;
import de.creditreform.common.helpers.TextFileUtils;
import de.creditreform.common.helpers.TextFileUtils.TextWriter;
import de.creditreform.common.pdf.Renderer;
import de.creditreform.common.xml.XmlAnonimizerEngine;
import de.creditreform.common.xml.XmlAnonimizerEngine.Result;
import de.creditreform.common.xml.utils.XmlTools;

/**
 * The Class BatchExecutor.
 * @author tavrovsa
 */
public class DataUpdater {
	private static final Logger log = LoggerFactory.getLogger(DataUpdater.class);

	/** The Constant batchSize. */
	private static final int PLAIN_XML = 1;
	private static final int COMPRESSED_XML = 2;

	/** The java.sql.Connection instance */
	private Connection conn;

	/** The db schema. */
	private String dbSchema;

	private static Charset CHARSET_UTF8;
	static {
		CHARSET_UTF8 = Charset.forName("UTF-8");
	}



	/**
	 * Instantiates a new batch executor.
	 *
	 * @param conn the java.sql.Connection instance
	 * @param dbSchema the database schema
	 */
	public DataUpdater(Connection conn, String dbSchema) {

		this.conn = conn;
		this.dbSchema = getCorrectSchema(dbSchema);

	}



	public void listAllXmls() throws SQLException, IOException {


		String baseQuery = "select %1 "+
				"FROM <schema>TBLXMLSTORAGE X WHERE "+
				"x.DOCUMENTCATEGORY IN (1,17,18) "+
				" AND (X.CREATIONDATE < sysdate OR X.CREATIONDATE is null)";

		baseQuery = baseQuery.replaceAll("<schema>", getCorrectSchema(dbSchema));

		long total = getTotalRowsToUpdate(baseQuery);

		if (total < 1) {
			log.info("Nothing to do. Exiting");
			return ;
		}

		CliProgress progr = new CliProgress(total);
		progr.start();

		//Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		Statement statement = conn.createStatement();
		String query = Args.fill(baseQuery, "X.CS_ID, X.STORAGETYPE, X.DOCUMENTCATEGORY, X.PDFSTORAGE_FK, X.XMLDATA, X.COMPRESSED, x.TRANSFORMEDDOCUMENT");


		ResultSet rs = statement.executeQuery(query);
		conn.setAutoCommit(false);
		String xmlDAta = null;
		rs.setFetchSize(128);

		BatchExecutor batchUpd = new BatchExecutor(conn, dbSchema);
		while (rs.next()) {
			progr.inc();

			xmlDAta = getData(rs, xmlDAta, rs.getInt("storagetype"));

			long csId = rs.getLong("CS_ID");
			long pdfId = rs.getLong("PDFSTORAGE_FK");

			batchUpd.addEntity(new BOEntryRow(csId, pdfId, xmlDAta));
			progr.drawProgress();

		}
		batchUpd.flush();
		batchUpd.close();
		rs.close();
		statement.close();
		progr.finish();

	}



	/**
	 * @param baseQuery
	 * @return
	 * @throws SQLException
	 */
	private long getTotalRowsToUpdate(String baseQuery) throws SQLException {
		long total = 0;

		try {

		PreparedStatement ps = conn.prepareStatement(Args.fill(baseQuery, "count(*) as cnt"));
		ResultSet rsi = ps.executeQuery();


		if (rsi.next())
		{
			total = rsi.getLong(1);
		}

		rsi.close();
		ps.close();

		} catch (Exception e) {
			log.error("",e);
		}


		return total;
	}



	/**
	 * @param res
	 * @param prettyXml
	 * @throws IOException
	 */
	private void writeFile(Result res, String prettyXml) throws IOException {
		TextWriter tw = TextFileUtils.createTextWriter("D:\\ReportResponse\\ReportResponse_"+res.getCrefoNr()+".xml", "UTF-8", false);
		tw.write(prettyXml);
		tw.close();
	}



	/**
	 * @param rs
	 * @param xmlDAta
	 * @param storageType
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private String getData(ResultSet rs, String xmlDAta, int storageType) throws SQLException, IOException {

		if (0 == storageType) storageType = PLAIN_XML;

		if (PLAIN_XML == storageType) {
			Clob aClob = rs.getClob("xmldata");
			xmlDAta = DbTools.clobToStr(aClob);
		} else if (COMPRESSED_XML == storageType) {
			Blob aBlob = rs.getBlob("compressed");
			xmlDAta = DbTools.zippedBlobToStr(aBlob);
		}
		return xmlDAta;
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
	 * Flush.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void flush() throws SQLException {
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


		public PreparedStatement getPreparedStatement() throws SQLException {

			String sql = "insert into #SCHEMA#TBLMBAGENCYMETADATA (CS_ID, VERSION, CREFONR, NAME1, NAME2, NAME3, STREET, HOMENR, ADDRESSEXT, ZIP, CITY, COUNTRYCODE,"+
					"COUNTRY, FOUNDATIONDATE, LEGALFORMCODE, LEGALFORM, INQUIRYSENDDATE, INQUIREYSENDTIME, ADDENDUMDEADLINE, INQUIRYREFNR,"+
					"BONI, PAYMENTFORM, CREDITJUDGMENT, ANNUALTURNOVER, AT_CURRENCY, TOTALASSETS, TA_CURRENCY, EMPLOYEESCOUNT, USERIDINS) values "+
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//			String sql = "insert into #SCHEMA#TBLMBAGENCYMETADATA (CS_ID, CREFONR, NAME1, NAME2, STREET) values (?, ?, ?, ?, ?)";
			sql = sql.replaceAll("#SCHEMA#", dbSchema);
			return conn.prepareStatement(sql);
		}


		/**
		 * Persist entries.
		 *
		 * @throws SQLException the sQL exception
		 */
		public void persistEntries() throws SQLException {

//			if (fifo.isEmpty()) {
//				return ;
//			}
//
//			BatchData element;
//			PreparedStatement stmt = getPreparedStatement();
//
//			while ((element = fifo.poll())!=null) {
//				if (persistEntry(stmt, element)) {
//					stmt.addBatch();
//					if (parent != null) parent.fifo.add(element);
//				}
//			}
//
//			stmt.executeBatch();
//			stmt.close();
//			conn.commit();
//
//			if (parent != null) {
//				parent.persistEntries();
//			} else {
//				conn.commit();
//			}
		}



}
