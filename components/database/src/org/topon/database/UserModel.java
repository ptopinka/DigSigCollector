package org.topon.database;

import java.security.cert.X509Certificate;

public class UserModel {

	private String uid;
	private String commonName;
	private X509Certificate certificate;
	private int idpodpis;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public void setIdpodpis(int idpodpis) {
		this.idpodpis = idpodpis;
	}

	public int getIdpodpis() {
		return this.idpodpis;
	}
	public X509Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CN= " +getCommonName());
		sb.append("\n");
		sb.append("uid= " + getUid());
		sb.append("\n");
		
		X509Certificate cert = getCertificate();
		if(cert != null) {
			sb.append("cert subject= " + getCertificate().getSubjectX500Principal().getName());
		
		} else {
			sb.append("user has no certificate, maybe it is  Revoked");
		}
		return sb.toString();
	}

}
