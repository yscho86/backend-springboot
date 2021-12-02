package cc.grum.base.backendspringboot.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	private final static Logger logger = LoggerFactory.getLogger(LogUtils.class);
	private final static boolean isLogging = true;
	
	public static String getLogTag() {
		int level = 3;
		
		StackTraceElement trace = Thread.currentThread().getStackTrace()[level];
		
		String fullClassName = trace.getClassName();
	    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	    // System.out.println("fullClassName["+level+"] = " + fullClassName);
	    // System.out.println("className["+level+"] = " + className);
	    
	    while( className.equals("LogUtils") ) {
	    	level++;
	    	trace = Thread.currentThread().getStackTrace()[level];
	    	fullClassName = trace.getClassName();
	    	className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		    // System.out.println("fullClassName["+level+"] = " + fullClassName);
		    // System.out.println("className["+level+"] = " + className);
	    }

	    String methodName = trace.getMethodName();
	    int lineNumber = trace.getLineNumber();

	    // String prefix = DateTimeUtils.getFormatedDT("yyyy-MM-dd HH:mm:ss") + " [" + className + "." + methodName + "():" + lineNumber + "] ";
	    StringBuffer prefix = new StringBuffer()
				.append("[")
				.append(className)
				.append(".")
				.append(methodName)
				.append("():")
				.append(lineNumber)
				.append("] ");
	    return prefix.toString();
	}

	public static void dStart() {
		LogUtils.d(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> START LOG" );
	}
	public static void dEnd() {
		LogUtils.d("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END LOG" );
	}
	public static void dLine() {
		LogUtils.d("----------------------------------------------------");
	}

	public static void d(String format, Object... args) {
		if (! isLogging) return;
		logger.debug(getLogTag() + format, args);
	}

	public static void i(String format, Object... args) {
		if (! isLogging) return;
		logger.info(getLogTag() + format, args);
	}

	public static void e(Exception paramException) {
		if (! isLogging || paramException==null) return;
		logger.error(getLogTag(), paramException.getMessage());
	}

	public static void e(String paramString) {
		if (! isLogging || paramString==null) return;
		logger.error(getLogTag(), paramString);
	}

	public static void e(String format, Object args) {
		if (! isLogging) return;
		logger.error(getLogTag() + format, args);
	}
	
	public static void memory() {
		if (! isLogging) return;
		logger.info(getLogTag() + "totalMemory: {}, maxMemory: {}, freeMemory: {}"
				, Runtime.getRuntime().totalMemory()
				, Runtime.getRuntime().maxMemory()
				, Runtime.getRuntime().freeMemory());
	}
	
}
