package at.fhooe.mcm.lri.smdm.server;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;

import org.apache.tools.ant.taskdefs.Sync.MyCopy;
import org.eclipse.jdt.internal.core.Openable;

import at.fhooe.mcm.lri.smdm.server.db.DBConnection;
import at.fhooe.mcm.lri.smdm.server.db.DBConnectionManager;
import at.fhooe.mcm.lri.smdm.server.db.DBDeviceInfo;
import at.fhooe.mcm.lri.smdm.server.db.DBUser;
import at.fhooe.mcm.lri.smdm.server.policy.CreateSMDMPolicy;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.TextFileProperty;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("policyServer_theme")
public class PolicyServer extends UI {

	// XXX @WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PolicyServer.class)
	public static class Servlet extends VaadinServlet {
	}

	String basepath = VaadinService.getCurrent().getBaseDirectory()
			.getAbsolutePath();
	// Image as a file resource
	FileResource resource = new FileResource(new File(basepath
			+ "/WEB-INF/images/image.png"));

	FilesystemContainer docs = new FilesystemContainer(new File(basepath
			+ "/content/"));
	ComboBox docList = new ComboBox("Documents", docs);
	Table tableFiles = new Table("Documents", docs);

	private AbsoluteLayout mainLayout;
	private TabSheet tabMain;
	private RichTextArea richTextArea_2;

	private HorizontalLayout layoutMain;

	private Panel panelMain;

	private VerticalLayout layoutPanelMain;

	private TabSheet tabPolicies;

	private Table tableRegisteredDevices;

	private Panel panelHeader;

	private VerticalLayout verticalLayout_1;

	private MenuBar menuBar;

	private GridLayout gridLayoutDeviceAdmin;

	private Label lblDisableCamera;
	private Label lblEncryption;
	private CheckBox cbxEncryption;

	private CheckBox cbxDisableCamera;

	private Label lblPasswordQuality;

	private AbstractComponent cbxHistoryLength;

	private Label lblHistoryLength;

	private ComboBox cbxPasswordQuality;

	private Label label_4;

	private AbstractComponent comboBox_2;

	private Label label_5;

	private AbstractComponent slider_1;
	private AbstractComponent slider_2;
	private AbstractComponent slider_3;
	private AbstractComponent slider_4;
	private AbstractComponent slider_5;
	private AbstractComponent slider_6;

	private Label label_6;

	private AbstractComponent nativeSelect_1;

	private Label label_7;

	private AbstractComponent textField_1;

	private Label label_8;

	private Label label_9;

	private DBConnection mConnection;

	private TextArea taDetails;

	@Override
	protected void init(VaadinRequest request) {
		// starting db connection
		String dbURL = "jdbc:mysql://localhost/userdb";
		String dbUser = "dbUsername";
//		String dbPwd = "dbPassword";
		String dbPwd = "";
		// String dbURL = "jdbc:mysql://mysql1.000webhost.com/a3102070_user";
		// String dbUser = "a3102070_user";
		// String dbPwd = "a3102070_password";
		try {
			mConnection = DBConnectionManager.getConnection(dbURL, dbUser,
					dbPwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showNotification("Cannot connect to DB",
					Notification.TYPE_ERROR_MESSAGE);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showNotification("Cannot connect to DB",
					Notification.TYPE_ERROR_MESSAGE);
		}
		
		buildMainLayout();
		setContent(mainLayout);

		initRegisteredDevicesTable();
		updateRegisteredDevices();
	}

	// set up the properties (columns)
	private void initRegisteredDevicesTable() {
		tableRegisteredDevices.addContainerProperty("Device ID:", String.class,
				"");
		tableRegisteredDevices.addContainerProperty("Account:", String.class,
				"");
	}

	// fill the table with some random data
	private void updateRegisteredDevices() {
		initRegisteredDevicesTable();
		if(mConnection==null) {
			showNotification("Cannot connect to DB");
			tableRegisteredDevices.addItem(new Object[] { "ERROR", "Cannot connect to DB" },
					new Integer(0));
			return;
		}
		List<DBUser> users = mConnection.getUsers();

		if (users != null) {
			System.out.println("Read users: " + users.size());
			if (users.size() > 0) {
				for (int i = 0; i < users.size(); i++) {
					DBUser user = users.get(i);

					tableRegisteredDevices
							.addItem(
									new Object[] { user.getDeviceId(),
											user.getEmail() }, new Integer(i));
				}
			} else {
				tableRegisteredDevices.addItem(new Object[] { "null", "null" },
						new Integer(0));
			}
		}
	}

	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		// top-level component properties
		setWidth("100%");
		setHeight("100.0%");

		// panelHeader
		panelHeader = buildPanelHeader();
		mainLayout.addComponent(panelHeader,
				"top:0.0px;left:0.0px;right:0.0px;");

		// layoutMain
		layoutMain = buildLayoutMain();
		mainLayout.addComponent(layoutMain,
				"top:50.0px;left:0.0px;right:0.0px;bottom:0.0px;");
		//
		// mainLayout.setExpandRatio(panelHeader, 1);
		// mainLayout.setExpandRatio(layoutMain, 4);

		return mainLayout;
	}

	private Panel buildPanelHeader() {
		// common part: create layout
		panelHeader = new Panel();
		panelHeader.setImmediate(false);
		panelHeader.setWidth("100.0%");

		// verticalLayout_1
		verticalLayout_1 = buildVerticalLayout_1();
		panelHeader.setContent(verticalLayout_1);

		return panelHeader;
	}

	private VerticalLayout buildVerticalLayout_1() {
		// common part: create layout
		verticalLayout_1 = new VerticalLayout();
		verticalLayout_1.setImmediate(false);
		verticalLayout_1.setWidth("100.0%");
		verticalLayout_1.setHeight("100.0%");
		verticalLayout_1.setMargin(false);

		// menuBar
		menuBar = new MenuBar();
		menuBar.setImmediate(false);
		menuBar.setWidth("100.0%");
//		menuBar.addItem("Reset Policies", new Command() {
//			@Override
//			public void menuSelected(MenuItem selectedItem) {
//				System.out.println("Reset Policies pressed");
//			}
//		});
		menuBar.addItem("Reload Registered Devices", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				System.out.println("Reload pressed");
				updateRegisteredDevices();
			}
		});
//		menuBar.addItem("UPDATE DEVICES", new Command() {
//			@Override
//			public void menuSelected(MenuItem selectedItem) {
//				System.out.println("UPDATE DEVICES pressed");
//			}
//		});
//		menuBar.addItem("TEST BUTTON", new Command() {
//			@Override
//			public void menuSelected(MenuItem selectedItem) {
//				System.out.println("TEST BUTTON");
//			}
//		});
		menuBar.addItem("Generate Policy", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				System.out.println("Generate Policy pressed");

				// getUI().getPage().open(
				// "/SMDM-PolicyServer3/CreateSMDMBundleServlet", "_new");
				if (CreateSMDMPolicy.createAndSafePolicyBundle()) {
					showNotification("New Policy Generated");
				} else {
					showNotification("Oops! Cannot generate new policy!\nPlease contact your administrator ;-)");
				}
				
				updateCurrentPolicyVersion();
			}
		});
		menuBar.addItem("Register", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				System.out.println("Register pressed");
				showNotification("Test");
				getUI().getPage().open("/SMDM-PolicyServer3/register", "_new");
			}
		});
		menuBar.addItem("Unregister", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				System.out.println("Unregister pressed");
				showNotification("Test");
				getUI().getPage()
						.open("/SMDM-PolicyServer3/unregister", "_new");
			}
		});

		verticalLayout_1.addComponent(menuBar);

		return verticalLayout_1;
	}

	private HorizontalLayout buildLayoutMain() {
		// common part: create layout
		layoutMain = new HorizontalLayout();
		layoutMain.setImmediate(false);
		layoutMain.setWidth("100.0%");
		layoutMain.setHeight("100.0%");
		layoutMain.setMargin(false);

		// tableRegisteredDevices
		tableRegisteredDevices = new Table();
		tableRegisteredDevices.setImmediate(false);
		tableRegisteredDevices.setCaption("Registered Devices:");
		tableRegisteredDevices.setDescription("Reg. Devices");
		tableRegisteredDevices.setWidth("300px");
		tableRegisteredDevices.setHeight("100.0%");
		tableRegisteredDevices.setSelectable(true);
		tableRegisteredDevices.setImmediate(true);
		final Label current = new Label("Selected: -");
		tableRegisteredDevices
				.addValueChangeListener(new Property.ValueChangeListener() {
					public void valueChange(ValueChangeEvent event) {
						try {
							current.setValue("Selected: "
									+ tableRegisteredDevices.getValue());

							int index = (Integer) tableRegisteredDevices
									.getValue();

							tabPolicies.setSelectedTab(0);

							String deviceID = (String) tableRegisteredDevices
									.getContainerProperty(index, "Device ID:")
									.getValue();
							System.out.println("Selected Device ID: "
									+ deviceID);

							DBDeviceInfo info = mConnection
									.getLatestDeviceInfo(deviceID);

							if (info == null) {
								taDetails.setValue("");
							} else {
								taDetails.setValue(info.toString());
							}
							// showNotification("selected: " + index);
						} catch (Exception e) {
							// / FUUUUUU
						}
					}
				});
		layoutMain.addComponent(tableRegisteredDevices);
		layoutMain.setComponentAlignment(tableRegisteredDevices, new Alignment(
				48));

		// panelMain
		panelMain = buildPanelMain();
		layoutMain.addComponent(panelMain);
		layoutMain.setExpandRatio(panelMain, 1.0f);
		layoutMain.setComponentAlignment(panelMain, new Alignment(48));

		return layoutMain;
	}

	private Panel buildPanelMain() {
		// common part: create layout
		panelMain = new Panel();
		panelMain.setImmediate(false);
		panelMain.setWidth("100.0%");
		panelMain.setHeight("100.0%");

		// layoutPanelMain
		layoutPanelMain = buildLayoutPanelMain();
		panelMain.setContent(layoutPanelMain);

		return panelMain;
	}

	private VerticalLayout buildLayoutPanelMain() {
		// common part: create layout
		layoutPanelMain = new VerticalLayout();
		layoutPanelMain.setImmediate(false);
		layoutPanelMain.setWidth("100.0%");
		layoutPanelMain.setHeight("100.0%");
		layoutPanelMain.setMargin(false);

		// tabPolicies
		buildTabPolicies();

		layoutPanelMain.addComponent(tabPolicies);

		return layoutPanelMain;
	}

	private void updateCurrentPolicyVersion() {
		if(mConnection== null) {
			showNotification("Cannot connect to DB");
		}else {
			tabPolicies.setCaption("Current Policy: " + mConnection.getLatestPolicyVersion());
		}
	}
	
	private void buildTabPolicies() {
		tabPolicies = new TabSheet();
		tabPolicies.setImmediate(false);
		tabPolicies.setCaption("Current Policy:");
		tabPolicies.setWidth("100.0%");
		tabPolicies.setHeight("100.0%");
		
		updateCurrentPolicyVersion();

		TextArea rtaSELinuxFileContext = new TextArea("File Contexts",
				new TextFileProperty(new File(basepath
						+ "/content/file_contexts")));
		TextArea rtaSELinuxPropertyContext = new TextArea("Property Contexts",
				new TextFileProperty(new File(basepath
						+ "/content/property_contexts")));
		TextArea rtaSELinuxSeappContext = new TextArea("SEApp Contexts",
				new TextFileProperty(new File(basepath
						+ "/content/seapp_contexts")));
		TextArea rtaSELinuxSepolicy = new TextArea("sepolicy",
				new TextFileProperty(new File(basepath + "/content/sepolicy")));

		TextArea taEops = new TextArea("Eops", new TextFileProperty(new File(
				basepath + "/content/eops.xml")));
		TextArea taAppOps = new TextArea("AppOps", new TextFileProperty(
				new File(basepath + "/content/appops.xml")));
		TextArea taIfw = new TextArea("IFW", new TextFileProperty(new File(
				basepath + "/content/ifw.xml")));
		TextArea taMacPerms = new TextArea("MacPerms", new TextFileProperty(
				new File(basepath + "/content/mac_permissions.xml")));
		TextArea taDeviceAdmin = new TextArea("MacPerms", new TextFileProperty(
				new File(basepath + "/content/device_admin_policy.json")));
		taDetails = new TextArea("Device Details", "No device selected");
		TextArea taContextPolicy = new TextArea("Context Policy",
				new TextFileProperty(new File(basepath
						+ "/content/context_policy")));

		taDetails.setSizeFull();
		taDeviceAdmin.setSizeFull();
		rtaSELinuxFileContext.setSizeFull();
		rtaSELinuxPropertyContext.setSizeFull();
		rtaSELinuxSeappContext.setSizeFull();
		rtaSELinuxSepolicy.setSizeFull();
		taEops.setSizeFull();
		taAppOps.setSizeFull();
		taIfw.setSizeFull();
		taMacPerms.setSizeFull();
		taContextPolicy.setSizeFull();

		tabPolicies.addTab(taDetails, "Details");
		tabPolicies.addTab(taDeviceAdmin, "Device Admin");

		// TODO
		tabPolicies.addTab(buildGridLayout_1(), "Device Admin NEW");

		tabPolicies.addTab(rtaSELinuxFileContext, "File Contexts");
		tabPolicies.addTab(rtaSELinuxPropertyContext, "Property Contexts");
		tabPolicies.addTab(rtaSELinuxSeappContext, "SEApp Contexts");
		tabPolicies.addTab(rtaSELinuxSepolicy, "SEPolicy");
		tabPolicies.addTab(taEops, "EOPS");
		tabPolicies.addTab(taAppOps, "AppOps");
		tabPolicies.addTab(taIfw, "IFW");
		tabPolicies.addTab(taMacPerms, "MacPerms");
		tabPolicies.addTab(taContextPolicy, "Context Policy");

	}

	private TabSheet buildTabDeviceAdmin() {
		TabSheet sheet = new TabSheet();
		sheet.setImmediate(true);
		sheet.setWidth("100%");
		sheet.setHeight("100%");
		sheet.addComponent(buildGridLayout_1());

		return sheet;
	}

	private GridLayout buildGridLayout_1() {
		// common part: create layout
		gridLayoutDeviceAdmin = new GridLayout();
		gridLayoutDeviceAdmin.setImmediate(false);
		gridLayoutDeviceAdmin.setWidth("100.0%");
		gridLayoutDeviceAdmin.setHeight("233px");
		gridLayoutDeviceAdmin.setMargin(false);
		gridLayoutDeviceAdmin.setColumns(2);
		gridLayoutDeviceAdmin.setRows(20);

		// label_1
		lblDisableCamera = new Label();
		lblDisableCamera.setImmediate(false);
		lblDisableCamera.setWidth("-1px");
		lblDisableCamera.setHeight("-1px");
		lblDisableCamera.setValue("Disable Camera");
		gridLayoutDeviceAdmin.addComponent(lblDisableCamera, 0, 0);

		// checkBox_1
		cbxDisableCamera = new CheckBox();
		cbxDisableCamera.setCaption("Disable Camera");
		cbxDisableCamera.setImmediate(false);
		cbxDisableCamera.setWidth("-1px");
		cbxDisableCamera.setHeight("-1px");
		gridLayoutDeviceAdmin.addComponent(cbxDisableCamera, 1, 0);

		// label_2
		lblPasswordQuality = new Label();
		lblPasswordQuality.setImmediate(false);
		lblPasswordQuality.setWidth("-1px");
		lblPasswordQuality.setHeight("-1px");
		lblPasswordQuality.setValue("Password Quality");
		gridLayoutDeviceAdmin.addComponent(lblPasswordQuality, 0, 1);

		// comboBox_1
		cbxPasswordQuality = new ComboBox();
		cbxPasswordQuality.setImmediate(false);
		cbxPasswordQuality.setWidth("-1px");
		cbxPasswordQuality.setHeight("-1px");
		cbxPasswordQuality.removeAllItems();
		cbxPasswordQuality.addItem("Unspecified");
		cbxPasswordQuality.select("Unspecified");
		cbxPasswordQuality.addItem("Biometrix weak");
		cbxPasswordQuality.addItem("Something");
		cbxPasswordQuality.addItem("Numeric");
		cbxPasswordQuality.addItem("Alphabetic");
		cbxPasswordQuality.addItem("Alphanumeric");
		cbxPasswordQuality.addItem("Complex");
		gridLayoutDeviceAdmin.addComponent(cbxPasswordQuality, 1, 1);

		// label_3
		lblHistoryLength = new Label();
		lblHistoryLength.setImmediate(false);
		lblHistoryLength.setWidth("-1px");
		lblHistoryLength.setHeight("-1px");
		lblHistoryLength.setValue("Password History Lenght");
		gridLayoutDeviceAdmin.addComponent(lblHistoryLength, 0, 2);

		// checkBox_2
		cbxHistoryLength = new Slider(0, 100);
//		cbxHistoryLength.setCaption("Password History Length");
		cbxHistoryLength.setImmediate(false);
		cbxHistoryLength.setWidth("-1px");
		cbxHistoryLength.setHeight("-1px");
		gridLayoutDeviceAdmin.addComponent(cbxHistoryLength, 1, 2);

		// label_4
		label_4 = new Label();
		label_4.setImmediate(false);
		label_4.setWidth("-1px");
		label_4.setHeight("-1px");
		label_4.setValue("Maximum Time To Lock");
		gridLayoutDeviceAdmin.addComponent(label_4, 0, 3);

		// comboBox_2
		comboBox_2 = new Slider(0, 10000);
		comboBox_2.setImmediate(false);
		comboBox_2.setWidth("-1px");
		comboBox_2.setHeight("-1px");
		gridLayoutDeviceAdmin.addComponent(comboBox_2, 1, 3);

		// label_5
		label_5 = new Label();
		label_5.setImmediate(false);
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue("Minimum Upper Case Letters");
		gridLayoutDeviceAdmin.addComponent(label_5, 0, 4);

		// slider_1
		slider_1 = new Slider(0, 20);
		slider_1.setImmediate(false);
		slider_1.setWidth("-1px");
		slider_1.setHeight("6px");
		gridLayoutDeviceAdmin.addComponent(slider_1, 1, 4);

		// label_6
		label_6 = new Label();
		label_6.setImmediate(false);
		label_6.setWidth("-1px");
		label_6.setHeight("-1px");
		label_6.setValue("Minimum Lower Case Letters");
		gridLayoutDeviceAdmin.addComponent(label_6, 0, 5);

		slider_2 = new Slider(0, 20);
		slider_2.setImmediate(false);
		slider_2.setWidth("-1px");
		slider_2.setHeight("6px");
		gridLayoutDeviceAdmin.addComponent(slider_2, 1, 5);

		// label_7
		label_7 = new Label();
		label_7.setImmediate(false);
		label_7.setWidth("-1px");
		label_7.setHeight("-1px");
		label_7.setValue("Minimum Length");
		gridLayoutDeviceAdmin.addComponent(label_7, 0, 6);

		slider_2 = new Slider(0, 40);
		slider_2.setImmediate(false);
		slider_2.setWidth("-1px");
		slider_2.setHeight("6px");
		gridLayoutDeviceAdmin.addComponent(slider_2, 1, 6);

		// label_8
		label_8 = new Label();
		label_8.setImmediate(false);
		label_8.setWidth("-1px");
		label_8.setHeight("-1px");
		label_8.setValue("Minimum Letters");
		gridLayoutDeviceAdmin.addComponent(label_8, 0, 7);

		slider_3 = new Slider(0, 40);
		slider_3.setImmediate(false);
		slider_3.setWidth("-1px");
		slider_3.setHeight("6px");
		gridLayoutDeviceAdmin.addComponent(slider_3, 1, 7);

		// label_9
		label_9 = new Label();
		label_9.setImmediate(false);
		label_9.setWidth("-1px");
		label_9.setHeight("-1px");
		label_9.setValue("Minimum Symbols");
		gridLayoutDeviceAdmin.addComponent(label_9, 0, 8);

		slider_4 = new Slider(0, 40);
		slider_4.setImmediate(false);
		slider_4.setWidth("-1px");
		slider_4.setHeight("6px");
		gridLayoutDeviceAdmin.addComponent(slider_4, 1, 8);

		// label_1
		lblEncryption = new Label();
		lblEncryption.setImmediate(false);
		lblEncryption.setWidth("-1px");
		lblEncryption.setHeight("-1px");
		lblEncryption.setValue("Encryption Storage");
		gridLayoutDeviceAdmin.addComponent(lblEncryption, 0, 9);

		// checkBox_1
		cbxEncryption = new CheckBox();
		cbxEncryption.setCaption("Encrypt Storage");
		cbxEncryption.setImmediate(false);
		cbxEncryption.setWidth("-1px");
		cbxEncryption.setHeight("-1px");
		gridLayoutDeviceAdmin.addComponent(cbxEncryption, 1, 9);

		return gridLayoutDeviceAdmin;
	}

}