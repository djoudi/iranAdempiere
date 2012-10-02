package de.muntjak.tinylookandfeel;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboPopup;

import org.compiere.swing.CComboBox;
import org.compiere.swing.CField;

public class TinyComboPopup extends BasicComboPopup {

	public TinyComboPopup(JComboBox combo)
	{
		super(combo);
	}   //  AdempiereComboPopup
	public void show()
	{
		//  Check ComboBox if popup should be displayed
		if (comboBox instanceof CComboBox && !((CComboBox)comboBox).displayPopup())
			return;
		//  Check Field if popup should be displayed
		if (comboBox instanceof CField && !((CField)comboBox).displayPopup())
			return;
		super.show();
	}   //  show


	/**
	 *  Inform CComboBox and CField that Popup was hidden
	 *  @see CComboBox.hidingPopup
	 *  @see CField.hidingPopup
	 *
	public void hide()
	{
		super.hide();
		//  Inform ComboBox that popup was hidden
		if (comboBox instanceof CComboBox)
			(CComboBox)comboBox).hidingPopup();
		else if (comboBox instanceof CComboBox)
			(CComboBox)comboBox).hidingPopup();
	}   //  hided
	/**/
	
	/** 
	 * @see javax.swing.plaf.basic.BasicComboPopup#getPopupHeightForRowCount(int) 
	 **/  
	@Override 
	protected int getPopupHeightForRowCount(int maxRowCount) 
	{ 
		// ensure the combo box sized for the amount of data to be displayed 
		int rows = comboBox.getItemCount() < comboBox.getMaximumRowCount() 
			?  comboBox.getItemCount() 
			:  comboBox.getMaximumRowCount() ;
		
		if (rows <= 0 ) rows = 1;
		return super.getPopupHeightForRowCount(1) * rows; 
	}
}
