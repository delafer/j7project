package de.creditreform.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.creditreform.common.cli.CliArgs;

public class ImporterActivity {


	private static final Logger log = LoggerFactory.getLogger(ImporterActivity.class);


	private CliArgs args;

	private boolean dbInitialized;
	private Connection conn;



	public ImporterActivity(CliArgs args) {
		this.args = args;
	}


	private void initJDBCDriver() {
		try {
			Class.forName(args.getDbDriver());
			dbInitialized = true;
		} catch (ClassNotFoundException e) {
			log.error("Can't initialize jdbc driver:", e);
		}
	}


	private Connection getConnection() throws SQLException {
		return getConnection(false);
	}

	private Connection getConnection(boolean forceNew) throws SQLException {

		if (forceNew  || conn == null || !conn.isValid(500)) {
			try {
				if (!dbInitialized) {
					initJDBCDriver();
				}
				conn = DriverManager.getConnection(args.getDbURL(), args.getDbUser(), args.getDbPassword());
				conn.setAutoCommit(false);

//				if (isFilled(dbSchema))
//					conn.setSchema(dbSchema);
			}catch(Exception e){
				log.error("Can't open jdbc connection:", e);
			}
		}


		return conn;
	}



	public void doWork() throws Exception {
		DataUpdater exec = new DataUpdater(this.getConnection(), args.getDbSchema());
		exec.listAllXmls();

	}






}
