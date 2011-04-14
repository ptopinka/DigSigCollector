package org.topon.signatures.jbean;

/**
 * Remote Bean Interface
 * @author topon
 *
 */

public interface Signature extends javax.ejb.EJBObject
{
	public int stockValue(int minNumber, int maxNumber) throws java.rmi.RemoteException; 
}
