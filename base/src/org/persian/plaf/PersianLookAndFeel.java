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

import java.awt.Color;
import java.awt.Component;

import javax.swing.UIDefaults;

import de.muntjak.tinylookandfeel.TinyDefaultTheme;
import de.muntjak.tinylookandfeel.TinyLookAndFeel;

/**
 * 
 * @author Omid Pourhadi Email : omidpourhadi AT gmail DOT com
 * 
 */
public class PersianLookAndFeel extends TinyLookAndFeel {

	public PersianLookAndFeel() {
		super();
	}

	/** The name */
	public static final String NAME = "Persian";

	private static TinyDefaultTheme s_persianTheme = new org.persian.plaf.PersianTheme();

	private static TinyDefaultTheme s_theme = s_persianTheme;

	/**
	 * The Name
	 * 
	 * @return Name
	 */
	public String getName() {
		return NAME;
	} // getName

	/**
	 * The ID
	 * 
	 * @return Name
	 */
	public String getID() {
		return NAME;
	} // getID

	/**
	 * The Description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return "Persian Look & Feel - (c) 2001-2005 Omidp";
	} // getDescription

	/**************************************************************************
	 * Get/Create Defaults
	 * 
	 * @return UI Defaults
	 */
	public UIDefaults getDefaults() {
		// com.jgoodies.looks.plastic.PlasticLookAndFeel.setCurrentTheme(s_theme);
		UIDefaults defaults = super.getDefaults(); // calls init..Defaults
		return defaults;
	} // getDefaults

	protected void initClassDefaults(UIDefaults table) {
		// System.out.println("AdempiereLookAndFeel.initClassDefaults");
		super.initClassDefaults(table);
		// Overwrite
		putDefault(table, "ComboBoxUI");
		putDefault(table, "LabelUI");
		putDefault(table, "TabbedPaneUI");
		putDefault(table, "MenuUI");
		putDefault(table, "MenuBarUI");
		putDefault(table, "MenuItemUI");

	} // initClassDefaults

	private void putDefault(UIDefaults table, String uiKey) {
		try {
			String className = "de.muntjak.tinylookandfeel.Tiny" + uiKey;
			table.put(uiKey, className);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} // putDefault

	protected void initSystemColorDefaults(UIDefaults table) {
		super.initSystemColorDefaults(table);
	} // initSystemColorDefaults

	protected void initComponentDefaults(UIDefaults table) {
		super.initComponentDefaults(table);

		// ComboBox defaults
		Color c = table.getColor("TextField.background");
		table.put("ComboBox.background", c);
		table.put("ComboBox.listBackground", c);

		// omidp
		// Class lf = com.jgoodies.looks.plastic.PlasticLookAndFeel.class;
		Class lf = de.muntjak.tinylookandfeel.TinyLookAndFeel.class;
		// table.put("Tree.openIcon", makeIcon(lf, "icons/TreeMinusIcon.png"));
		// table.put("Tree.closedIcon", makeIcon(lf, "icons/TreePlusIcon.png"));
		table.put("Tree.leafIcon", makeIcon(lf, "icons/TreeLeafIcon.png"));

	} // initComponentDefaults

	/**
	 * Set Current Theme
	 * 
	 * @param theme
	 *            metal theme
	 */
	public static void setCurrentTheme(TinyDefaultTheme theme) {
		if (theme != null) {
			s_theme = theme;
			TinyLookAndFeel.setCurrentTheme(s_theme);
		}
	} // setCurrentTheme

	/**
	 * Get Current Theme
	 * 
	 * @return Metal Theme
	 */
	public static TinyDefaultTheme getCurrentTheme() {
		return s_theme;
	} // getCurrentTheme

	public static TinyDefaultTheme getDefaultTheme() {
		return s_persianTheme;
	} // getCurrentTheme

	public void provideErrorFeedback(Component component) {
		super.provideErrorFeedback(component);
	} // provideErrorFeedback

}
