package com.utility;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages {

	public final static ResourceBundle bundle = ResourceBundle.getBundle("messages");

	public static void addMessage(String summary, String type) {
		FacesMessage message = new FacesMessage(summary, null);

		if (type == "info") {
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		} else if (type == "error") {
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		} else if (type == "warn") {
			message.setSeverity(FacesMessage.SEVERITY_WARN);
		} else if (type == "fatal") {
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
		} else {
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}

		requestContext().addMessage(null, message);
	}

	private static FacesContext requestContext() {
		return FacesContext.getCurrentInstance();
	}

}
