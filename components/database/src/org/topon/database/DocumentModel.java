package org.topon.database;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DocumentModel {

	public static final String DOCUMENTS_EL = "documents";
	public static final String DOC_EL = "doc";
	public static final String METADATA_EL = "metadata";
	public static final String DESCRIPTION_EL = "description";

	/** attribute name */
	public static final String DOCIID_ATT = "docid";
	/** attribute name */
	public static final String DOCURL_ATT = "docurl";
	/** attribute name */
	public static final String VALIDSTART_ATT = "validstart";
	/** attribute name */
	public static final String VALIDEND_ATT = "validend";
	/** attribute to description name */
	public static final String TEXT_ATT = "text";

	public static final int VALID_START_DATE = 0;
	public static final int VALID_END_DATE = 1;
	
	/*
	 * filetype,hash,type,filename
	 */
	
	
	/** document id, primary key from database*/
	private int docid;
	/** url of the document to sign*/
	private String docURL;
	/** Document is valid from this date*/
	private long validStart;
	/** Document is valid to this date*/
	private long validEnd;
	/** Description of the document */
	private String description;
	/** Type of the file pdf, doc...*/
	private String filetype;


	/** sha1 hash od the document*/
	private String hash;
	/** type (intern, extern)*/
	private String type;
	/** File name*/
	private String filename;


	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	public String getDescription() {
		if (description != null) {
			return description;
		} else {
			return new String();
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public String getDocURL() {
		return docURL;
	}

	public void setDocURL(String docURL) {
		this.docURL = docURL;
	}

	public long getValidEnd() {
		return validEnd;
	}

	public void setValidEnd(long validEnd) {
		this.validEnd = validEnd;
	}

	public long getValidStart() {
		return validStart;
	}

	public void setValidStart(long validStart) {
		this.validStart = validStart;
	}

	public String toString() {
		return getDocURL() + "  -  " + new Date(getValidStart()).toString()
				+ " - " + new Date(getValidEnd()).toString();
	}

	private GregorianCalendar getCalendar (long timestamp) {
		GregorianCalendar calendar= new GregorianCalendar();
		calendar.setTimeInMillis(timestamp);
		return calendar;
	}
	
	/**
	 * Return Calendar.field from validity Date. When getting CALENDAR.month  
	 * Be aware that GregorianCalendar coutns month since 0 to 11 
	 * @param valid_type Type of the validity (END | START) See @VALID_START_DATE constant
	 * @param  Calendar type filed (Calendar.YEAR, Calendar.MONTH, Calendar.DATE)
	 * @return int value of type field
	 */
	public int getCalendarField(int valid_type, int calendarTypeField ) {
		long date = 0;
		switch (valid_type) {
		case  DocumentModel.VALID_START_DATE:
			date = getValidStart();
			break;
		case  DocumentModel.VALID_END_DATE:
			date = getValidEnd();
			break;
		default:
			throw new IllegalArgumentException("Wrong argumemt valid_type, Should be DocumentModel.VALID_START_DATE, DocumentModel.VALID_END_DATE");
		}
		Calendar calendar = getCalendar(date);
		int out = calendar.get(calendarTypeField);
		//monts are returned from zero (0) and I need it like real month number
		return out;
	}


}
