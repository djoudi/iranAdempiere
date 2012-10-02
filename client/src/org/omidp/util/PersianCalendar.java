/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.omidp.util;

/**
 * 
 * @author Omid Pourhadi Email : omidpourhadi AT gmail DOT com
 * 
 */
public class PersianCalendar {

	public static String CONSTANT_Delimiter = "/";

	public static final double PERSIAN_EPOCH = 1948320.5;;

	public static final double GREGORIAN_EPOCH = 1721425.5;

	/**
	 * @param solarDateAsTimeStamp
	 *            .toString()
	 * @return gregorian date format e.g 2008-03-31 or this method customize for
	 *         adempiere
	 */
	public String SolarToGregorian(String solarDateAsTimeStamp) {
		if (solarDateAsTimeStamp.indexOf("-") != -1) {
			CONSTANT_Delimiter = "-";
		} else {
			CONSTANT_Delimiter = "/";
		}
		if (solarDateAsTimeStamp.length() > 0) {
			String[] dateElement = solarDateAsTimeStamp.toString().split(
					CONSTANT_Delimiter);
			//
			int m_currentYear = Integer.parseInt(dateElement[0]);
			int m_currentMonth = Integer.parseInt(dateElement[1]);
			String[] daywithtime = dateElement[2].split(" ");
			int m_currentDay = Integer.parseInt(daywithtime[0]);

			// in some date format like 08 year is less than 1387
			if (m_currentYear < 1300)
				m_currentYear += 1300;

			String day, month, year;

			day = Integer.toString(m_currentDay);
			month = Integer.toString(m_currentMonth);
			year = Integer.toString(m_currentYear);

			if (m_currentDay < 10)
				day = "0" + day;
			if (m_currentMonth < 10)
				month = "0" + month;

			if (daywithtime.length > 2) {
				solarDateAsTimeStamp = convertJdnToGregorian(convertPersianToJdn(
						m_currentYear, m_currentMonth, m_currentDay))
						+ " "
						+ daywithtime[1] + " " + daywithtime[2];
				solarDateAsTimeStamp = changeFormat(solarDateAsTimeStamp) + " "
						+ daywithtime[1] + " " + daywithtime[2];
			} else if (daywithtime.length > 1 && daywithtime.length < 3)
				solarDateAsTimeStamp = convertJdnToGregorian(convertPersianToJdn(
						m_currentYear, m_currentMonth, m_currentDay))
						+ " "
						+ daywithtime[1];
			else
				solarDateAsTimeStamp = convertJdnToGregorian(convertPersianToJdn(
						m_currentYear, m_currentMonth, m_currentDay));

		}

		return solarDateAsTimeStamp;

	}

	/**
	 * @param gregorianDateAsTimeStamp
	 *            .toString() format supported 08/07/17 10:00 AM, 2008/07/17,
	 *            2008-03-31 12:59:00.00, 2008-07-17 this is main method of
	 *            convertion other methods use this
	 * @return solar date format e.g 1387-01-01
	 */
	public String GregorianToSolar(String gregorianDateAsTimeStamp) {

		if (gregorianDateAsTimeStamp.indexOf("-") != -1) {
			CONSTANT_Delimiter = "-";
		} else {
			CONSTANT_Delimiter = "/";
		}
		if (gregorianDateAsTimeStamp.length() > 0) {
			String[] dateElement = gregorianDateAsTimeStamp.toString().split(
					CONSTANT_Delimiter);
			int m_currentYear = Integer.parseInt(dateElement[0]);

			int m_currentMonth = Integer.parseInt(dateElement[1]);
			String[] daywithtime = dateElement[2].split(" ");
			int m_currentDay = Integer.parseInt(daywithtime[0]);

			/**
			 * this check is for short simple date format 7/25/08 10:00 AM after
			 * format fix we don not need to use this in solar method again
			 * */
			if (daywithtime.length > 2) {
				m_currentYear = Integer.parseInt(daywithtime[0]);
				m_currentMonth = Integer.parseInt(dateElement[0]);
				m_currentDay = Integer.parseInt(dateElement[1]);
			}

			String day, month, year;

			day = Integer.toString(m_currentDay);
			month = Integer.toString(m_currentMonth);
			year = Integer.toString(m_currentYear);

			if (m_currentDay < 10)
				day = "0" + day;
			if (m_currentMonth < 10)
				month = "0" + month;

			/** usin supprot for this format 7/25/08 10:00 AM */
			if (daywithtime.length > 2)
				gregorianDateAsTimeStamp = convertJdnToPersian(convertGregorianToJdn(
						m_currentYear, m_currentMonth, m_currentDay))
						+ " "
						+ daywithtime[1] + " " + daywithtime[2];
			/** 2008-03-31 12:59:00.00 */
			else if (daywithtime.length > 1 && daywithtime.length < 3)
				gregorianDateAsTimeStamp = convertJdnToPersian(convertGregorianToJdn(
						m_currentYear, m_currentMonth, m_currentDay))
						+ " "
						+ daywithtime[1];
			/** 2008-07-17, 2008/07/17 */
			else
				gregorianDateAsTimeStamp = convertJdnToPersian(convertGregorianToJdn(
						m_currentYear, m_currentMonth, m_currentDay));

		}

		return gregorianDateAsTimeStamp;

	}

	/**
	 * @param gYear
	 * @param gMonth
	 * @param gDay
	 * @return
	 */
	public String GregorianToSolar(int gYear, int gMonth, int gDay) {
		String compondDate = String.valueOf(gYear) + CONSTANT_Delimiter
				+ String.valueOf(gMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(gDay);
		return GregorianToSolar(compondDate);
	}

	/**
	 * @param gregorianDate
	 *            as timestamp.toString()
	 * @return
	 */
	public int getSolarYear(String gregorianDate) {
		int solarYear = 0;
		String[] spliterDate = GregorianToSolar(gregorianDate).split(
				CONSTANT_Delimiter);
		solarYear = Integer.parseInt(spliterDate[0]);
		return solarYear;
	}

	/**
	 * @param gYear
	 * @param gMonth
	 * @param gDay
	 * @return
	 */
	public int getSolarYear(int gYear, int gMonth, int gDay) {
		int solarYear = 0;
		String gregorianDate = String.valueOf(gYear) + CONSTANT_Delimiter
				+ String.valueOf(gMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(gDay);
		String[] spliterDate = GregorianToSolar(gregorianDate).split(
				CONSTANT_Delimiter);
		solarYear = Integer.parseInt(spliterDate[0]);
		return solarYear;
	}

	/**
	 * @param gregorianDate
	 *            as timestamp.toString()
	 * @return
	 */
	public int getSolarMonth(String gregorianDate) {
		int solarMonth = 0;
		String[] spliterDate = GregorianToSolar(gregorianDate).split(
				CONSTANT_Delimiter);
		solarMonth = Integer.parseInt(spliterDate[1]);
		return solarMonth;
	}

	/**
	 * @param gYear
	 * @param gMonth
	 * @param gDay
	 * @return
	 */
	public int getSolarMonth(int gYear, int gMonth, int gDay) {
		int solarMonth = 0;
		String gregorianDate = String.valueOf(gYear) + CONSTANT_Delimiter
				+ String.valueOf(gMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(gDay);
		String[] spliterDate = GregorianToSolar(gregorianDate).split(
				CONSTANT_Delimiter);
		solarMonth = Integer.parseInt(spliterDate[1]);
		return solarMonth;
	}

	/**
	 * @param gregorianDate
	 *            as timestamp.toString()
	 * @return
	 */
	public int getSolarDay(String gregorianDate) {
		int solarDay = 0;
		String[] spliterDate = GregorianToSolar(gregorianDate).split(
				CONSTANT_Delimiter);
		solarDay = Integer.parseInt(spliterDate[2]);
		return solarDay;
	}

	/**
	 * @param gYear
	 * @param gMonth
	 * @param gDay
	 * @return
	 */
	public int getSolarDay(int gYear, int gMonth, int gDay) {
		int solarDay = 0;
		String gregorianDate = String.valueOf(gYear) + CONSTANT_Delimiter
				+ String.valueOf(gMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(gDay);
		String[] spliterDate = GregorianToSolar(gregorianDate).split(
				CONSTANT_Delimiter);
		solarDay = Integer.parseInt(spliterDate[2]);
		return solarDay;
	}

	/**
	 * @param solarYear
	 * @param solarMonth
	 * @param solarDay
	 * @return only return gregorian year
	 */
	public int getGregorianYear(int sYear, int sMonth, int sDay) {
		int gregorianYear = 0;
		String solarDate = String.valueOf(sYear) + CONSTANT_Delimiter
				+ String.valueOf(sMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(sDay);
		String[] spliterDate = SolarToGregorian(solarDate).split(
				CONSTANT_Delimiter);
		gregorianYear = Integer.parseInt(spliterDate[0]);
		return gregorianYear;
	}

	/**
	 * @param solarYear
	 * @param solarMonth
	 * @param solarDay
	 * @return only return gregorian Month
	 */
	public int getGregorianMonth(int sYear, int sMonth, int sDay) {
		int gregorianMonth = 0;
		String solarDate = String.valueOf(sYear) + CONSTANT_Delimiter
				+ String.valueOf(sMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(sDay);
		String[] spliterDate = SolarToGregorian(solarDate).split(
				CONSTANT_Delimiter);
		gregorianMonth = Integer.parseInt(spliterDate[1]);
		return gregorianMonth;
	}

	/**
	 * @param solaarYear
	 * @param solarMonth
	 * @param solarDay
	 * @return only return gregorian day
	 */
	public int getGregorianDay(int sYear, int sMonth, int sDay) {
		int gregorianDay = 0;
		String solarDate = String.valueOf(sYear) + CONSTANT_Delimiter
				+ String.valueOf(sMonth).concat(CONSTANT_Delimiter)
				+ String.valueOf(sDay);
		String[] spliterDate = SolarToGregorian(solarDate).split(
				CONSTANT_Delimiter);
		gregorianDay = Integer.parseInt(spliterDate[2]);
		return gregorianDay;
	}

	public static boolean Leap_Persian(int year) {
		return ((((((year - ((year > 0) ? 474 : 473)) % 2820) + 474) + 38) * 682) % 2816) < 682;
	}

	// LEAP_GREGORIAN -- Is a given year in the Gregorian calendar a leap year ?
	public boolean Leap_Gregorian(double year) {
		return ((year % 4) == 0)
				&& (!(((year % 100) == 0) && ((year % 400) != 0)));
	}

	/** Convert to JDN */

	// GREGORIAN_TO_JD -- Determine Julian day number from Gregorian calendar
	// date
	public double convertGregorianToJdn(double year, double month, int day) {
		return (GREGORIAN_EPOCH - 1)
				+ (365 * (year - 1))
				+ Math.floor((year - 1) / 4)
				+ (-Math.floor((year - 1) / 100))
				+ Math.floor((year - 1) / 400)
				+ Math.floor((((367 * month) - 362) / 12)
						+ ((month <= 2) ? 0 : (Leap_Gregorian(year) ? -1 : -2))
						+ day);
	}

	// JULIAN_TO_JD -- Determine Julian day number from Julian calendar date
	public double convertJulianToJdn(int year, int month, int day) {
		/* Adjust negative common era years to the zero-based notation we use. */

		if (year < 1) {
			year++;
		}

		/*
		 * Algorithm as given in Meeus, Astronomical Algorithms, Chapter 7, page
		 * 61
		 */

		if (month <= 2) {
			year--;
			month += 12;
		}

		return ((Math.floor((365.25 * (year + 4716)))
				+ Math.floor((30.6001 * (month + 1))) + day) - 1524.5);
	}

	// PERSIAN_TO_JD -- Determine Julian day from Persian date
	public double convertPersianToJdn(double year, double month, double day) {
		double epbase, epyear;

		epbase = year - ((year >= 0) ? 474 : 473);
		epyear = 474 + mod(epbase, 2820);

		return day
				+ ((month <= 7) ? ((month - 1) * 31) : (((month - 1) * 30) + 6))
				+ Math.floor(((epyear * 682) - 110) / 2816) + (epyear - 1)
				* 365 + Math.floor(epbase / 2820) * 1029983
				+ (PERSIAN_EPOCH - 1);
	}

	/** JDN Converts to other */
	/**
	 * @param jdn
	 * @return
	 */
	public String convertJdnToPersian(double jd) {
		double year, month, day, depoch, cycle, cyear, ycycle, aux1, aux2, yday;

		jd = Math.floor(jd) + 0.5;

		depoch = jd - convertPersianToJdn(475, 1, 1);
		cycle = Math.floor(depoch / 1029983);
		cyear = mod(depoch, 1029983);
		if (cyear == 1029982) {
			ycycle = 2820;
		} else {
			aux1 = Math.floor(cyear / 366);
			aux2 = mod(cyear, 366);
			ycycle = Math
					.floor(((2134 * aux1) + (2816 * aux2) + 2815) / 1028522)
					+ aux1 + 1;
		}
		year = ycycle + (2820 * cycle) + 474;
		if (year <= 0) {
			year--;
		}
		yday = (jd - convertPersianToJdn(year, 1, 1)) + 1;
		month = (yday <= 186) ? Math.ceil(yday / 31) : Math
				.ceil((yday - 6) / 30);
		day = (jd - convertPersianToJdn(year, month, 1)) + 1;
		String strYear = Integer.toString((int) year), strMonth = Integer
				.toString((int) month), strDay = Integer.toString((int) day);

		if (strMonth.length() < 2)
			strMonth = "0" + strMonth;
		if (strDay.length() < 2)
			strDay = "0" + strDay;

		return strYear + CONSTANT_Delimiter + strMonth + CONSTANT_Delimiter
				+ strDay;
	}

	/**
	 * @param jdn
	 * @return
	 */
	public String convertJdnToGregorian(double jd) {
		double wjd, depoch, quadricent, dqc, cent, dcent, quad, dquad, yindex, dyindex, year, yearday, leapadj;

		wjd = Math.floor(jd - 0.5) + 0.5;
		depoch = wjd - GREGORIAN_EPOCH;
		quadricent = Math.floor(depoch / 146097);
		dqc = mod(depoch, 146097);
		cent = Math.floor(dqc / 36524);
		dcent = mod(dqc, 36524);
		quad = Math.floor(dcent / 1461);
		dquad = mod(dcent, 1461);
		yindex = Math.floor(dquad / 365);
		year = (quadricent * 400) + (cent * 100) + (quad * 4) + yindex;
		if (!((cent == 4) || (yindex == 4))) {
			year++;
		}
		yearday = wjd - convertGregorianToJdn(year, 1, 1);
		leapadj = ((wjd < convertGregorianToJdn(year, 3, 1)) ? 0
				: (Leap_Gregorian(year) ? 1 : 2));
		double month = Math.floor((((yearday + leapadj) * 12) + 373) / 367);
		double day = (wjd - convertGregorianToJdn(year, month, 1)) + 1;

		String strYear = Integer.toString((int) year), strMonth = Integer
				.toString((int) month), strDay = Integer.toString((int) day);

		if (strMonth.length() < 2)
			strMonth = "0" + strMonth;
		if (strDay.length() < 2)
			strDay = "0" + strDay;

		return strYear + CONSTANT_Delimiter + strMonth + CONSTANT_Delimiter
				+ strDay;
	}

	/**
	 * @param jdn
	 * @return
	 */
	public String convertJdnToJulian(double jdn) {
		double z, a, alpha, b, c, d, e, year, month, day, td = 0;

		td += 0.5;
		z = Math.floor(td);

		a = z;
		b = a + 1524;
		c = Math.floor((b - 122.1) / 365.25);
		d = Math.floor(365.25 * c);
		e = Math.floor((b - d) / 30.6001);

		month = Math.floor((e < 14) ? (e - 1) : (e - 13));
		year = Math.floor((month > 2) ? (c - 4716) : (c - 4715));
		day = b - d - Math.floor(30.6001 * e);

		/*
		 * If year is less than 1, subtract one to convert from a zero based
		 * date system to the common era system in which the year -1 (1 B.C.E)
		 * is followed by year 1 (1 C.E.).
		 */

		if (year < 1) {
			year--;
		}
		String strYear = Integer.toString((int) year), strMonth = Integer
				.toString((int) month), strDay = Integer.toString((int) day);

		if (strMonth.length() < 2)
			strMonth = "0" + strMonth;
		if (strDay.length() < 2)
			strDay = "0" + strDay;

		return strYear + CONSTANT_Delimiter + strMonth + CONSTANT_Delimiter
				+ strDay;
	}

	public double mod(double args1, double args2) {
		return args1 % args2;
	}

	/**
	 * @param Date
	 *            input as 1387-04-26 7:59 AM use in solartogregorian method to
	 *            change format to 7/16/08 7:59 AM
	 * @return gregorian date format as 7/16/08 7:59 AM
	 */
	public String changeFormat(String Date) {
		String[] fixFormat = Date.split(CONSTANT_Delimiter);
		String newMonth = fixFormat[1].substring(1);
		String newDay = fixFormat[2].substring(0, fixFormat[2].indexOf(" "));
		String newYear = fixFormat[0].substring(2);
		return newMonth + "/" + newDay + "/" + newYear;
	}
}