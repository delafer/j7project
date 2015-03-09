package de.creditreform.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.creditreform.common.cli.CliArgs;
import de.creditreform.common.xml.model.reader.AnonymizeRulesReader;

public class ImporterActivity {


	private static final Logger log = LoggerFactory.getLogger(ImporterActivity.class);


	private CliArgs args;


	public ImporterActivity(CliArgs args) {
		this.args = args;
	}


	public void doWork() throws Exception {

		AnonymizeRulesReader reader = new AnonymizeRulesReader();
		reader.read(args.getXmlRules());

		DataUpdater dataUpdater = new DataUpdater(args);
		dataUpdater.startJob();

	}






}
