package org.topon.signatures.servlet;


import javax.servlet.*;
import javax.servlet.http.*;

/** Some simple time savers.
 *  Part of tutorial on servlets and JSP that appears at
 *  http://www.apl.jhu.edu/~hall/java/Servlet-Tutorial/
 *  1999 Marty Hall; may be freely used or adapted.
 */

public class ServletUtilities {
	public static final String DOCUMENT_ACTION = "docaction";
	
	public static final int DOCUMENT_ADD = 0;
	public static final int DOCUMENT_DEL = 1;
	public static final int DOCUMENT_EDIT = 2;
	
	public static final String USER_ACTION = "useraction";
	public static final int USER_ADD = 3;
	public static final int USER_DEL = 4;
	public static final int USER_EDIT = 5;
	public static final int USER_REVOKE = 6;
	public static final int USER_VALIDATE = 7;
	
	
	
	public static final String YEAR_START = "yearstart";
	public static final String MONTH_START = "monthstart";
	public static final String DAY_START = "daystart";
	
	public static final String YEAR_END = "yearend";
	public static final String MONTH_END = "monthend";
	public static final String DAY_END = "dayend";
	
	
	public static final String DOCTYPE =
    "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";

  public static String headWithTitle(String title) {
    return(DOCTYPE + "\n" +
           "<HTML>\n" +
           "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
  }

  /** Read a parameter with the specified name, convert it to an int,
      and return it. Return the designated default value if the parameter
      doesn't exist or if it is an illegal integer format.
  */
  
  public static int getIntParameter(HttpServletRequest request,
                                    String paramName,
                                    int defaultValue) {
    String paramString = request.getParameter(paramName);
    int paramValue;
    try {
      paramValue = Integer.parseInt(paramString);
    } catch(NumberFormatException nfe) { // Handles null and bad format
      paramValue = defaultValue;
    }
    return(paramValue);
  }

  public static String getCookieValue(Cookie[] cookies,
                                      String cookieName,
                                      String defaultValue) {
    for(int i=0; i<cookies.length; i++) {
      Cookie cookie = cookies[i];
      if (cookieName.equals(cookie.getName()))
        return(cookie.getValue());
    }
    return(defaultValue);
  }

  
}