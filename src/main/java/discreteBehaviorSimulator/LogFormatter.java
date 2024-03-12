package discreteBehaviorSimulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author Flavien Vernier
 *
 */
public class LogFormatter  extends Formatter {
	
	/**
	* This method is used to format a log record.
	* 
	* @param a LogRecord object
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
	* @param milliseconds in type long
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
	  * @param a Handler object
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
	  * @param a Handler object
	  * @return an empty String
	  */
	  public String getTail(Handler h) {
	    return "";
	  }

}
