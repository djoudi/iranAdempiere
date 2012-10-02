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

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.util.Locale;

import org.compiere.util.Language;

/**
 * 
 * @author Omid Pourhadi Email : omidpourhadi AT gmail DOT com
 * 
 */
public class LocaleUtil {

	public static Locale getIranLocale() {
		return new Locale(IranConstants.LOCALE_LANGUAGE,
				IranConstants.LOCALE_COUNTRY);
	}

	public static boolean isIranLocale() {
		return Language.getLoginLanguage().getLocale().equals(getIranLocale());
	}

	public static boolean isIranLocale(Locale loc) {
		return loc.equals(getIranLocale());
	}

	public static void applyComponentOrientation(Component... components) {
		for (Component component : components) {
			component.setComponentOrientation(ComponentOrientation
					.getOrientation(Language.getLoginLanguage().getLocale()));
		}
	}

}
