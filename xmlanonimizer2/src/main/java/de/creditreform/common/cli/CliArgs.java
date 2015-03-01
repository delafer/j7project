package de.creditreform.common.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CliArgs {


	private String dbDriver;
	private String dbURL;
	private String dbUser;
	private String dbPassword;
	private String dbSchema;

	public CliArgs(String fileName) throws FileNotFoundException, IOException {

		File f = new File(fileName);
		if (!f.exists()) throw new FileNotFoundException();
		read(fileName);

	}

	private void read(String fileName) throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(fileName));

		this.dbSchema = p.getProperty(EntryPointCLI.IMPORTER_DB_SCHEMA);
		this.dbDriver = p.getProperty(EntryPointCLI.IMPORTER_JDBC_DRIVER);
		this.dbURL = p.getProperty(EntryPointCLI.IMPORTER_JDBC_URL);
		this.dbUser = p.getProperty(EntryPointCLI.IMPORTER_DB_USER);
		this.dbPassword = p.getProperty(EntryPointCLI.IMPORTER_DB_PASSWORD);
	}

	/**
	 * @return the dbDriver
	 */
	public String getDbDriver() {
		return dbDriver;
	}

	/**
	 * @return the dbURL
	 */
	public String getDbURL() {
		return dbURL;
	}

	/**
	 * @return the dbUser
	 */
	public String getDbUser() {
		return dbUser;
	}

	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * @return the dbSchema
	 */
	public String getDbSchema() {
		return dbSchema;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("ArgsParser [dbDriver=%s, dbURL=%s, dbUser=%s, dbPassword=%s, dbSchema=%s]", dbDriver,
				dbURL, dbUser, "XXXXX", dbSchema);
	}



}
