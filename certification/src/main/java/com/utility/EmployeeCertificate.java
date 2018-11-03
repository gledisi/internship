package com.utility;

public class EmployeeCertificate {

	public static int setStatusOfCertifcate(int points) {
		int statusId = 0;
		if (points < 40) {
			statusId = 3;
		} else {
			statusId = 2;
		}
		return statusId;
	}

}
