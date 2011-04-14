package org.topon.signatures.jbean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StocksServlet extends HttpServlet {
	SignatureHome home;
	Signature stock;

	public void init(ServletConfig config) throws ServletException{
		System.out.println("servlet init");
		//Look up home interface
		Hashtable env = new Hashtable(); 
		env.put(Context.INITIAL_CONTEXT_FACTORY,        "org.jnp.interfaces.NamingContextFactory"); 
		//Host at which JBoss server is listening
		env.put(Context.PROVIDER_URL, "jnp://localhost:1099");  
		env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces"); 

		try 
		{ 
			Context ctx = new InitialContext(env);
			Object obj = ctx.lookup( "org/topon/signatures" ); 
			home = (SignatureHome)javax.rmi.PortableRemoteObject.narrow( obj, SignatureHome.class );  
			if(home == null) {
				System.out.println("home is null");
			}
		} 

		catch ( Exception e ) 
		{ 
			e.printStackTrace(); 
			System.out.println( "Exception: " + e.getMessage() );
		} 


	}

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
	throws ServletException, IOException
	{

		PrintWriter out;
		response.setContentType("text/html");
		String title = "EJB Example";
		out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Stocks Servlet!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p align=\"center\"><font size=\"4\" color=\"#000080\">Servlet Calling Session Bean</font></p>");


		try{
			stock = home.create(); 
			out.println("<p align=\"center\"> Stock value for today is <b>" + stock.stockValue(5,100) + "</b></p>");
			stock.remove();
		}catch(Exception CreateException){
			CreateException.printStackTrace();
		}
		out.println("<p align=\"center\"><a href=\"javascript:history.back()\">Go to Home</a></p>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public void destroy() {
		System.out.println("Destroy");
	}
}
