package org.topon.signatures.jbean;
import java.util.Random;

import javax.ejb.SessionContext;

public class SignatureBean implements javax.ejb.SessionBean
{

	
	
	private SessionContext ctx;
	
	public void setSessionContext(SessionContext ctx)
	{
		//hovno
		this.ctx = ctx;
	}

	public void ejbRemove()
	{
		System.out.println( "ejbRemove()" );
	}

	public void ejbActivate()
	{
		System.out.println( "ejbActivate()" );
	}

	public void ejbPassivate()
	{
		System.out.println( "ejbPassivate()" );
	}

	/* The method called to display the stock value on the client. */
	public int stockValue(int minNumber, int maxNumber)
	{
		System.out.println( "stockValue()" );
		Random generator = new Random();
//		get the range, casting to long to avoid overflow problems
		long range = (long)maxNumber - (long)minNumber + 1;
//		compute a fraction of the range, 0 <= frac < range
		long fraction = (long)(range * generator.nextDouble());
		return (int)(fraction + minNumber);
	}

	public void ejbCreate()
	{
		System.out.println( "ejbCreate()" );
	}

}
