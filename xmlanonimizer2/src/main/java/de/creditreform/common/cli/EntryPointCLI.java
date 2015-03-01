package de.creditreform.common.cli;

import org.apache.commons.cli.*;
import org.slf4j.Logger;

import de.creditreform.common.db.ImporterActivity;
import de.creditreform.common.helpers.StringUtils;

public class EntryPointCLI {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EntryPointCLI.class);

	private static CommandLineParser parser;
	private static Options options;

	public static String IMPORTER_DB_SCHEMA = "jdbc.schema";
	public static String IMPORTER_DB_PASSWORD = "jdbc.password";
	public static String IMPORTER_DB_USER = "jdbc.user";
	public static String IMPORTER_JDBC_URL = "jdbc.url";
	public static String IMPORTER_JDBC_DRIVER = "jdbc.driver";


	public EntryPointCLI() {
	}


	private static void printHelp() {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( EntryPointCLI.class.getSimpleName() + " main class", options );

		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.LF).append(StringUtils.LF).
		append(">> Config example [dbConnection.properties]: <<").append(StringUtils.LF).
		append(IMPORTER_JDBC_DRIVER+" = oracle.jdbc.driver.OracleDriver").append(StringUtils.LF).
		append(IMPORTER_JDBC_URL+" = jdbc:oracle:thin:@tux121b004:1521:QUALITY").append(StringUtils.LF).
		append(IMPORTER_DB_USER+" = <db_user>").append(StringUtils.LF).
		append(IMPORTER_DB_PASSWORD+" = <db_pass>").append(StringUtils.LF).
		append(IMPORTER_DB_SCHEMA+" = <n5_schema>").append(StringUtils.LF).
		append(StringUtils.LF);

		System.out.println(sb.toString());
	}


	public static void main(String[] args) {

		parser = new PosixParser();

		options = new Options();
		options.addOption("?", "help", false, "show help.");
		options.addOption("c", "config", true, "configuration file (.properties)");

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("?")) {
				printHelp();
			} else
			// validate that block-size has been set
			if (!line.hasOption("c")) {
				// print the value of block-size
				throw new ParseException("you should specify a configuration file");
			}

			String configFile = line.getOptionValue("c");

			System.out.println(">>>"+configFile);
			CliArgs arp = new CliArgs(configFile);
			System.out.println(">>>"+arp);

			long start = System.currentTimeMillis();
			doTask(arp);
			long end = System.currentTimeMillis();

			toConsole("Task done in "+Math.round(0.001d * (end-start))+" seconds!");

		} catch (Exception exp) {
			logger.error("", exp);
			showError("Task failed.  Reason: " + exp.getMessage(), exp, true);
		}



		System.exit(0);


	}


	private static void doTask(CliArgs arp) throws Exception {
		ImporterActivity importer = new ImporterActivity(arp);
		importer.doWork();


//		Configuration cfg = HibernateUtil.getConfiguration();
//		importer.setInputDirectoryPath(destinationDir);
//		importer.setDBDriver(nvl(cfg.getProperty(CONFIG_HIBERNATE_DRIVER), CONFIG_DEFAULT_DRIVER));
//		importer.setDBURL(cfg.getProperty(CONFIG_HIBERNATE_URL));
//		importer.setDBUser(cfg.getProperty(CONFIG_HIBERNATE_USER));
//		importer.setDBPassword(cfg.getProperty(CONFIG_HIBERNATE_PWD));
//		importer.setDBSchema(CONFIG_DB_SCHEMA_NAME);
//
//		importer.doWork();

	}


	private static void showError(String errorText, Exception exp, boolean showHelp) {
		if (showHelp) {
			printHelp();
		}

		// oops, something went wrong
		toConsole();
		toConsole();
		System.err.println("Task failed.  Reason: " + exp.getMessage());
		System.exit(-1);
	}


	private static void toConsole() {
		toConsole("");
	}

	private static void toConsole(String string) {
		logger.info(string);
	}

}
