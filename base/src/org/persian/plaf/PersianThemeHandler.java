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
package org.persian.plaf;

import javax.swing.plaf.ColorUIResource;

import de.muntjak.tinylookandfeel.TinyLookAndFeel;

/**
 * 
 * @author Omid Pourhadi Email : omidpourhadi AT gmail DOT com
 * 
 */
public class PersianThemeHandler extends PersianTheme {

	public PersianThemeHandler() {
		super();
		setDefault();
		s_theme = this;
		s_name = NAME;
	}

	/** Name */
	public static final String NAME = "TinyLaF Default Theme";

	/**
	 * Set Defaults
	 */
	public void setDefault() {

		/** Blue 51,51,102 */
		primary0 = new ColorUIResource(103, 152, 203);
		/** Blue 102, 102, 153 */
		// protected static ColorUIResource primary1;
		primary1 = new ColorUIResource(101, 138, 187);
		/** Blue 153, 153, 204 */
		primary2 = new ColorUIResource(103, 152, 203);
		/** Blue 204, 204, 255 */
		primary3 = new ColorUIResource(233, 238, 245); //

		/** Black */
		// secondary0 = new ColorUIResource(0, 0, 0);
		/** Gray 102, 102, 102 */
		// protected static ColorUIResource secondary1;
		secondary1 = new ColorUIResource(190, 179, 153);
		/** Gray 153, 153, 153 */
		// protected static ColorUIResource secondary2;
		secondary2 = new ColorUIResource(246, 239, 224);
		/** BlueGray 214, 224, 234 - background */
		// protected static ColorUIResource secondary3;
		secondary3 = new ColorUIResource(251, 248, 241);
		/** White */
		// secondary4 = new ColorUIResource(255, 255, 255);

		/** Black */
		black = TinyLookAndFeel.getBlack();
		/** White */
		white = TinyLookAndFeel.getWhite();

		/** Background for mandatory fields */
		mandatory = new ColorUIResource(233, 238, 245); // blueish
		/** Background for fields in error 180,220,143 */
		error = new ColorUIResource(220, 241, 203); // green ;
		/** Background for inactive fields */
		inactive = new ColorUIResource(241, 239, 222);// 241,239,222
		/** Background for info fields */
		info = new ColorUIResource(251, 248, 251); // somewhat white

		/** Foreground Text OK */
		txt_ok = new ColorUIResource(0, 153, 255); // blue ;
		/** Foreground Text Error */
		txt_error = new ColorUIResource(255, 0, 51); // red ;

		/** Black */
		// secondary0 = new ColorUIResource(0, 0, 0);
		/** Control font */
		controlFont = null;
		_getControlTextFont();
		/** System font */
		systemFont = null;
		_getSystemTextFont();
		/** User font */
		userFont = null;
		_getUserTextFont();
		/** Small font */
		smallFont = null;
		_getSubTextFont();
		/** Window Title font */
		windowFont = null;
		_getWindowTitleFont();
		/** Menu font */
		menuFont = null;
		_getMenuTextFont();
	} // setDefault

}
