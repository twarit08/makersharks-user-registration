package makersharks.user.config;

import java.util.Date;

public class ExceptionDetail {

	private Date date;

	private String message;

	private String details;

	public ExceptionDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionDetail(Date date, String message, String details) {
		super();
		this.date = date;
		this.message = message;
		this.details = details;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	

}
