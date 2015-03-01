package de.creditreform.common.cli;


public class CliProgress {





/** The Constant MILLISECOND. */
   public final static long MILLISECOND = 1l; // ms

   public final static long SECOND = MILLISECOND * 1000l; // ms

   public final static long MINUTE = SECOND * 60l; // ms


   private final static long MAX_REDRAW_DELAY = MINUTE * 5l;
   private static final double MAX_PROGRESS_DELAY = 15.375d;

   private final static long MIN_REDRAW_DELAY = MINUTE ;
   private static final double MIN_PROGRESS_DELAY = 0.2d;

	private long current;
	private long total;


	private double oldProgress;
	private long oldTimestamp;



	public CliProgress(long total) {
		this.total = total;
	}


	public void inc() {
		current++;
	}

	private boolean readrawRequired() {
		if  ((System.currentTimeMillis() - oldTimestamp)>MAX_REDRAW_DELAY) return true;
		double prg = progress();
		if ((prg-oldProgress)>MAX_PROGRESS_DELAY) return true;
		if  ((System.currentTimeMillis() - oldTimestamp)>MIN_REDRAW_DELAY && (prg-oldProgress)>MIN_PROGRESS_DELAY) return true;
		return false;
	}

	public void drawProgress() {
		if (readrawRequired()) {
			updateValues();
			drawProgressInConsole();
		}
	}


	private void updateValues() {
		oldProgress = progress();
		oldTimestamp = System.currentTimeMillis();

	}


	private void drawProgressInConsole() {
		StringBuilder sb = new StringBuilder(33);
		sb.append("Done ").append(progress()).append(" % (").append(current).append(" / ").append(total).append(")");
		System.out.println(sb);
	}


	private double progress() {
		return round(((double)current / (double)total) * 100.0d, 2);
	}


    public static double round(double value, int precision)
    {
      if (precision > 0) {
        final double exp = Math.pow(10, precision);
        return Math.round(value * exp) / exp;
      }
      else
        return Math.round(value);
    }


	public void start() {
		this.oldTimestamp = 0L;
		this.current = 0;
		drawProgress();
	}


	public void finish() {
		if (oldProgress>99.99001d) return ;
		this.oldTimestamp = 0L;
		this.current = total;
		drawProgress();

	}




}
