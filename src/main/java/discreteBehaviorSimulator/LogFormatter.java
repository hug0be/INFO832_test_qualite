package discreteBehaviorSimulator;

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
		StringBuffer buf = new StringBuffer();
		
		buf.append(calcDate(rec.getMillis()));
		buf.append(": ");
		buf.append(rec.getLevel());
		buf.append(System.getProperty("line.separator"));
		buf.append(formatMessage(rec));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}

	/**
	* Calculates and formats the date.
	*
	* @param millisecs in type long
	* @return the corresponding date in type String
	*/
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
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
