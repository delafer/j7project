package org.delafer.xanderView.gui.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.delafer.xanderView.scale.ScaleFactory;

import net.j7.commons.io.FilePath;
import net.j7.commons.io.FilePath.PathType;

public class ApplConfiguration {


	public static final String CFG_POS_X = "PosX";
	public static final String CFG_POS_Y = "PosY";
	public static final String CFG_WIDTH = "Width";
	public static final String CFG_HEIGHT = "Height";
	public static final String SCALER_FAST = "scale.speed";
	public static final String SCALER_QUALITY = "scale.quality";

	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient ApplConfiguration INSTANCE = new ApplConfiguration();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public static ApplConfiguration instance() {
		return Holder.INSTANCE;
	}


	private Properties properties;
	private boolean unsaved;
	private String config;

	private ApplConfiguration() {
		unsaved = false;
		initialize();
	}

	private Properties defaults() {
		Properties pro = new Properties();
		pro.setProperty(CFG_HEIGHT, String.valueOf(600));
		pro.setProperty(CFG_WIDTH, String.valueOf(800));
		pro.setProperty(SCALER_FAST, String.valueOf(ScaleFactory.SCALER_AWT_2D_FAST ));
		return pro;
	}

	private void initialize() {
		config = FilePath.as().dir(PathType.ApplicationData).dir("xanderView").file("xander.properties").forceExists().build();
		properties = new Properties(defaults());
		try {
		  properties.load(new FileInputStream(config));
		} catch (IOException e) {
		  e.printStackTrace();
		}

//	    Timer timer = new Timer("config"+System.identityHashCode(this), true) {
//	    	{
//	    	}
//	    };
	    TimerTask task = new TimerTask() {
			public void run() {
				if (ApplConfiguration.this.isDirty()) {
					ApplConfiguration.this.save();
				}
			}

	    };

	    //timer.schedule( task, 5000, 5000 );
	    ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(threadFactory);
	    timer.scheduleWithFixedDelay(task, 5, 8, TimeUnit.SECONDS);
	}


	public String get(String key) {
		return properties.getProperty(key);
	}

	public long getLong(String key) {
		try {
			return Long.parseLong(get(key));
		} catch (Exception i) { return 0L; }
	}

	public int getInt(String key) {
		try {
			return Integer.parseInt(get(key));
		} catch (Exception i) { return 0; }
	}

	public String[] getValues(String key) {
		String str = get(key);
		return str.split(";");
	}

	public double getDouble(String key) {
		try {
			return Double.parseDouble(get(key));
		} catch (Exception i) { return 0d; }
	}

	public boolean getBoolean(String key) {
		try {
			return Boolean.parseBoolean(get(key));
		} catch (Exception i) {
			return false;
		}
	}

	public boolean isDirty() {
		return unsaved;
	}

	public void set(String key, Object value) {
		String toSet = value != null ? value.toString() : "null" ;
		if (toSet.equals(get(key))) return ;
		unsaved = true;
		properties.setProperty(key, toSet);
	}

	public void set(String key, String[] values) {
		StringBuilder sb = new StringBuilder();
		int j = 0;
		for (String next : values) {
			if (j++>0) sb.append(';');
			sb.append(next);
		}
		set(key, sb);
	}

	public void save() {
		try {
			FileOutputStream fos = new FileOutputStream(config);
			properties.store(fos, null);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		unsaved = false;

	}

	private final ThreadFactory threadFactory = new ThreadFactory() {

		final static int prio = (int)((Thread.MIN_PRIORITY  + Thread.NORM_PRIORITY ) / 2);

	    public Thread newThread(Runnable r) {
	        Thread t = new Thread(r, "Cfg-"+System.identityHashCode(ApplConfiguration.this));
	        t.setDaemon(true);
	        t.setPriority(prio);
	        return t;
	    }
	};

}
