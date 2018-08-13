package com.utility;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger LOGGER = LogManager.getLogger(CustomExceptionHandler.class.getName());

	private ExceptionHandler wrapped;

	CustomExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {

		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			// get the exception from context
			Throwable t = context.getException();

			final FacesContext fc = FacesContext.getCurrentInstance();
			final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			final NavigationHandler nav = fc.getApplication().getNavigationHandler();

			try {
				LOGGER.fatal("Fatal Error!Message : " + t.getMessage());
				requestMap.put("exceptionMessage", t.getMessage());
				nav.handleNavigation(fc, null, "/generalError?faces-redirect=true");
				fc.renderResponse();

			} finally {
				i.remove();
			}
		}
		// parent handle
		getWrapped().handle();
	}
}