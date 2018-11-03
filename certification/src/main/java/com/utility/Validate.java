package com.utility;

import java.util.Calendar;
import java.util.Date;

public class Validate {

	public static boolean dateOfCertification(Date inputDate) {

		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();

		calendar.add(Calendar.YEAR, 1);
		Date nextYear = calendar.getTime();

		if (inputDate.before(today) || inputDate.after(nextYear)) {
			Messages.addMessage(Messages.bundle.getString("CERTIFICATION_DATE"), "error");
			return false;
		}
		return true;
	}

	public static boolean birthdayOfEmployee(Date inputDate) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, -17);
		Date minAge = calendar.getTime();

		calendar.add(Calendar.YEAR, -65);
		Date maxAge = calendar.getTime();

		if (inputDate.after(minAge) || inputDate.before(maxAge)) {

			Messages.addMessage(Messages.bundle.getString("EMPLOYEE_BIRTHDAY"), "error");
			return false;

		}
		return true;
	}

}
