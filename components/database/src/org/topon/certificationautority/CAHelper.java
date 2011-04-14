package org.topon.certificationautority;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;

import javax.security.auth.x500.X500Principal;

import sun.security.x509.CRLDistributionPointsExtension;

public class CAHelper {
	

	/**
	 * Validate user certificate that it was issued by CA 
	 * @param userCertificate
	 * @return true when user certificate was issued by CA, otherwise it return
	 * false
	 */
	public static boolean validateCertificateWithCA(X509Certificate userCert) {
		boolean isValid = false;
		
		
		
		
		//TODO implement validation of user Certificate with CA certificate
		return isValid;
	}
	
	/**
	 * Checking whether user certificate is on CRL or not
	 * @param userCert
	 * @return true when user certificate is on CRL
	 */
	public static boolean isRevoked( X509Certificate userCert) {
		boolean isRevoked = false;
		
		//TODO implement checking that user certificate is on CRL
		return isRevoked;
	}
	
	   /**
     * Returns true if given two certificates are equal. This method 
     * compares the subject and the signature of the two certificates.
     */
    public static boolean isCertEqual(X509Certificate cert1, X509Certificate cert2) {

        boolean equal = false;

        try {
            BigInteger serial1 = cert1.getSerialNumber();
            BigInteger serial2 = cert2.getSerialNumber();

            // both serial numbers match
            if ( (serial1 != null) && (serial1.equals(serial2)) ) {

                X500Principal p1 = cert1.getSubjectX500Principal();
                X500Principal p2 = cert2.getSubjectX500Principal();

                // found matching principal
                if ((p1 != null) && (p1.equals(p2))) {

                    // found matching signature
                    if ( isSignatureEqual(cert1, cert2) ) {
                        equal = true;
                    }
                }
            }
        } catch (Exception e) {
            equal = false;
        }

        return equal;
    }

    /**
     * Returns true if signature of given two certificates are equal.
     *
     * @param  c1  first X509Certificate to be compared
     * @param  c2  second X509Certificate to be compared
     *
     * @return  true if signature matches 
     */
    private static boolean isSignatureEqual(X509Certificate c1, X509Certificate c2) {

        boolean equal = false;

        try {
            byte[] s1  = c1.getSignature();
            byte[] s2  = c2.getSignature();
        
            // both signature length is same
            if (s1.length == s2.length) {
                boolean  allBytesEqual = true;
                for (int j=0; j<s1.length; j++) {
                    if (s1[j] != s2[j]) {
                        allBytesEqual = false;
                        break;
                    }
                }
                // both signature are same
                if (allBytesEqual) {
                    equal = true;
                }
            }
        } catch (Exception e) {
            equal = false;
        }

        return equal;
    }

}
