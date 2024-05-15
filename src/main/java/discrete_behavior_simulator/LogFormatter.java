package discrete_behavior_simulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * The LogFormatter class is a formatter for logging records in a customized format.
 * It extends the Formatter class provided by the java.util.logging package.
 * <p>
 * This formatter formats log records by including the timestamp, log level, and log message.
 *
 * @author Flavien Vernier
 */
public class LogFormatter  extends Formatter {

	/**
	* This method is used to format a log record.
	* 
	* @param rec LogRecord object
	* @return the contents of the log record in type String
	*/
	public String format(LogRecord rec) {
		return calcDate(rec.getMillis()) +
				": " + rec.getLevel() +
				System.lineSeparator() +
				formatMessage(rec) +
				System.lineSeparator();
	}

	/**
	* Calculates and formats the date.
	*
	* @param millisec in type long
	* @return the corresponding date in type String
	*/
	String calcDate(long millisec) {
		if (millisec > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
			Date resultdate = new Date(millisec);
			return dateFormat.format(resultdate);
		}
		else{
			return null;
		}
	  }

	  // this method is called just after the handler using this
	  // formatter is created
	  /**
	  * This method is called just after the handler using this. formatter is created
	  * 
	  * @param h Handler object
	  * @return an empty String
	  */
	  public String getHead(Handler h) {
		  return "";
	  }
	  
	// this method is called just after the handler using this
	  // formatter is closed
	  /**
	  * This method is called jus after the handler using this. formatter is closed
	  * 
	  * @param h Handler object
	  * @return an empty String
	  */
	  public String getTail(Handler h) {
	    return "";
	  }

}
