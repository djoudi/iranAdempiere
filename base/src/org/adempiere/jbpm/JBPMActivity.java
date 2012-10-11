package org.adempiere.jbpm;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;

import org.compiere.apps.AEnv;
import org.compiere.apps.AMenu;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VLookup;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.swing.CTextField;
import org.compiere.swing.CTextPane;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * @author omid pourhadi
 * omidpourhadi@gmail.com
 *
 */
public class JBPMActivity extends CPanel implements FormPanel, ActionListener {

	public JBPMActivity() {
		super();
	}

	public JBPMActivity(AMenu menu) {
		super();
		log.config("");
		try {
			dynInit(0);
			jbInit();
		} catch (Exception e) {
			log.log(Level.SEVERE, "", e);
		}
		m_menu = menu;
	}

	/** Window No */
	private int m_WindowNo = 0;
	/** FormFrame */
	private FormFrame m_frame = null;
	/** Menu */
	private AMenu m_menu = null;

	/** Logger */
	private static CLogger log = CLogger.getCLogger(JBPMActivity.class);

	//
	private CPanel centerPanel = new CPanel();
	private GridBagLayout centerLayout = new GridBagLayout();
	private CLabel lNode = new CLabel(Msg.translate(Env.getCtx(),
			"AD_WF_Node_ID"));
	private CTextField fNode = new CTextField();
	private CLabel lDesctiption = new CLabel(Msg.translate(Env.getCtx(),
			"Description"));
	private CTextArea fDescription = new CTextArea();
	private CLabel lHelp = new CLabel(Msg.translate(Env.getCtx(), "Help"));
	private CTextArea fHelp = new CTextArea();
	private CLabel lHistory = new CLabel(Msg.translate(Env.getCtx(), "History"));
	private CTextPane fHistory = new CTextPane();
	private CLabel lAnswer = new CLabel(Msg.getMsg(Env.getCtx(), "Answer"));
	private CPanel answers = new CPanel(new FlowLayout(FlowLayout.LEADING));
	// private CTextField fAnswerText = new CTextField();
	private CComboBox fAnswerList = new CComboBox();
	 private CButton fAnswerButton = new CButton("Start Task");
	private CButton bPrevious = AEnv.getButton("Previous");
	private CButton bNext = AEnv.getButton("Next");
	private CButton bZoom = AEnv.getButton("Zoom");
	private CLabel lTextMsg = new CLabel(Msg.getMsg(Env.getCtx(), "Messages"));
	private CTextArea fTextMsg = new CTextArea();
	private CButton bOK = ConfirmPanel.createOKButton(true);
	private VLookup fForward = null; // dynInit
	private CLabel lForward = new CLabel(Msg.getMsg(Env.getCtx(), "Forward"));
	private CLabel lOptional = new CLabel("("
			+ Msg.translate(Env.getCtx(), "Optional") + ")");
	private StatusBar statusBar = new StatusBar();

	private ManagedJbpmContext jbpmHelper;
	private List<TaskInstance> taskInstanceList;
	private TaskInstance m_activity=null;
	private String userName = Env.getCtx().getProperty("#AD_User_Name");;

	/** Current Activity */
	private int m_index = 0;

	private void dynInit(int WindowNo) {
		jbpmHelper = new ManagedJbpmContext();
		loadActivities();
		//
		display();
		// Forward
		fForward = VLookup.createUser(WindowNo);
	} // dynInit

	private void jbInit() throws Exception {
		centerPanel.setLayout(centerLayout);
		centerPanel.applyComponentOrientation(ComponentOrientation.getOrientation(Language.getLoginLanguage().getLocale()));
		fNode.setReadWrite(false);
		fDescription.setReadWrite(false);
		fDescription.setPreferredSize(new Dimension(130, 40));
		fHelp.setReadWrite(false);
		fHelp.setPreferredSize(new Dimension(150, 80));
		fHistory.setReadWrite(false);
		fHistory.setPreferredSize(new Dimension(150, 60));
		fTextMsg.setPreferredSize(new Dimension(150, 40));
		//
		bPrevious.addActionListener(this);
		bNext.addActionListener(this);
		bZoom.addActionListener(this);
		bOK.addActionListener(this);
		//
		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.SOUTH);
		//
		// answers.setOpaque(false);
		//answers.add(fAnswerText);
		answers.add(fAnswerList);
		 answers.add(fAnswerButton);
		 fAnswerButton.addActionListener(this);
		//
		centerPanel.add(lNode, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fNode, new GridBagConstraints(1, 0, 2, 1, 0.5, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 5), 0, 0));
		centerPanel.add(bPrevious, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 10), 0, 0));

		centerPanel.add(lDesctiption, new GridBagConstraints(0, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fDescription, new GridBagConstraints(1, 1, 2, 1, 0.0,
				0.1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
		centerPanel.add(bNext, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 10), 0, 0));

		centerPanel.add(lHelp, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fHelp, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.1,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lHistory, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fHistory, new GridBagConstraints(1, 3, 3, 1, 0.5, 0.5,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lAnswer, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
						10, 10, 5, 5), 0, 0));
		centerPanel.add(answers, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 5, 5), 0, 0));
		centerPanel.add(bZoom, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
						10, 0, 10, 10), 0, 0));

		centerPanel.add(lTextMsg, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fTextMsg, new GridBagConstraints(1, 5, 3, 1, 0.5, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lForward, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
						10, 10, 5, 5), 0, 0));
		centerPanel.add(fForward, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 0, 5, 0), 0, 0));
		centerPanel.add(lOptional, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 5, 5, 5), 0, 0));
		centerPanel.add(bOK, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(
						10, 5, 5, 10), 0, 0));
	} // jbInit

	public void dispose() {
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;

	}

	public void init(int WindowNo, FormFrame frame) {
		m_WindowNo = WindowNo;
		m_frame = frame;
		//
		log.info("");
		try {
			dynInit(WindowNo);
			jbInit();
			//
			// this.setPreferredSize(new Dimension (400,400));
			frame.getContentPane().add(this, BorderLayout.CENTER);
			display();
		} catch (Exception e) {
			log.log(Level.SEVERE, "", e);
		}

	}

	public void display() {
		
		fAnswerList.removeAllItems();
		
		if (taskSize == 0) {
			// lStatus.setText("");
			fNode.setText("");
			fDescription.setText("");
			fHistory.setText("");
			statusBar.setStatusDB("0/0");
			statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "WFNoActivities"));
			bNext.setEnabled(false);
			fAnswerButton.setVisible(false);
			fAnswerList.setVisible(false);
			bOK.setEnabled(false);
			bZoom.setEnabled(false);
			bPrevious.setEnabled(false);
			if (m_menu != null)
				m_menu.updateJbpmActivities(0);
			return;
		} 
		else 
		{
			if (m_index >= taskSize - 1) 
			{
				m_index = taskSize - 1;
				bNext.setEnabled(false);
			} 
			else
				bNext.setEnabled(true);
			if (m_index <= 0) 
			{
				m_index = 0;
				bPrevious.setEnabled(false);
			} 
			else
				bPrevious.setEnabled(true);
			
			m_activity = taskInstanceList.get(m_index);
			Node taskNode = m_activity.getToken().getNode();
			ProcessInstance pi =  m_activity.getProcessInstance();
			fNode.setText(taskNode.getName());
			fDescription.setText(taskNode.getDescription());
			//fHelp.setText(ti.get)
			//fHistory.setText(ti.get)
			fAnswerList.setVisible(true);
			fAnswerButton.setVisible(true);
			bZoom.setEnabled(true);
			bOK.setEnabled(true);
			if (m_activity.getStart() != null)
			{									
				fAnswerButton.setEnabled(false);
			}
			
			if (m_menu != null)
			{
			 	m_menu.updateJbpmActivities(taskSize);
			}
			statusBar.setStatusDB((m_index+1) + "/" + taskSize);
			statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "WFActivities"));
			
//			List<Transition> transitionList =  jbpmHelper.listTransitionsForTasks(jbpmContext, m_activity.getId());
//			for (Transition transition : transitionList) {
//				fAnswerList.addItem(transition.getName());
//			}
		}

	}

	public int loadActivities() {
		taskInstanceList = jbpmHelper.getTaskInstanceList(userName);
		taskSize = taskInstanceList.size();
		//jbpmContext.close();
		return 0;
	}

	private int taskSize = 0;

	public int getActivitiesCount() {
		return taskSize;
	}

	public void actionPerformed(ActionEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if (e.getSource() == bNext || e.getSource() == bPrevious) {
			if (e.getSource() == bNext)
				m_index++;
			else
				m_index--;
			display();
		}
		else if (e.getSource() == bZoom)
			cmd_zoom();
		else if (e.getSource() == bOK)
			cmd_OK();
		else if (e.getSource() == fAnswerButton)
			cmd_startTask();
		this.setCursor(Cursor.getDefaultCursor());

	}

	private void cmd_startTask() {		
		 jbpmHelper.startTask();
		 display();
	}

	private void cmd_OK() {
		String signal = fAnswerList.getSelectedItem().toString();
		if(signal != null)
			m_activity.end(signal);
		else
			m_activity.end();
		
//		jbpmHelper.save();
		
//		Next
		loadActivities();
		display();
		
	}

	private void cmd_zoom() {
		// TODO Auto-generated method stub
		
	}

}