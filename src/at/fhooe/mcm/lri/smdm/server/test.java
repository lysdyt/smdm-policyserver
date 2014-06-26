package at.fhooe.mcm.lri.smdm.server;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;

public class test extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private GridLayout gridLayout_1;
	@AutoGenerated
	private Label label_9;
	@AutoGenerated
	private Label label_8;
	@AutoGenerated
	private TextField textField_1;
	@AutoGenerated
	private Label label_7;
	@AutoGenerated
	private NativeSelect nativeSelect_1;
	@AutoGenerated
	private Label label_6;
	@AutoGenerated
	private Slider slider_1;
	@AutoGenerated
	private Label label_5;
	@AutoGenerated
	private ComboBox comboBox_2;
	@AutoGenerated
	private Label label_4;
	@AutoGenerated
	private ComboBox comboBox_1;
	@AutoGenerated
	private Label label_3;
	@AutoGenerated
	private CheckBox checkBox_2;
	@AutoGenerated
	private Label label_2;
	@AutoGenerated
	private CheckBox checkBox_1;
	@AutoGenerated
	private Label label_1;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public test() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// gridLayout_1
		gridLayout_1 = buildGridLayout_1();
		mainLayout.addComponent(gridLayout_1,
				"top:0.0px;right:-3.0px;left:0.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_1() {
		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setImmediate(false);
		gridLayout_1.setWidth("100.0%");
		gridLayout_1.setHeight("233px");
		gridLayout_1.setMargin(false);
		gridLayout_1.setColumns(2);
		gridLayout_1.setRows(20);
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Label");
		gridLayout_1.addComponent(label_1, 0, 0);
		
		// checkBox_1
		checkBox_1 = new CheckBox();
		checkBox_1.setCaption("CheckBox");
		checkBox_1.setImmediate(false);
		checkBox_1.setWidth("-1px");
		checkBox_1.setHeight("-1px");
		gridLayout_1.addComponent(checkBox_1, 1, 0);
		
		// label_2
		label_2 = new Label();
		label_2.setImmediate(false);
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("Label");
		gridLayout_1.addComponent(label_2, 0, 1);
		
		// checkBox_2
		checkBox_2 = new CheckBox();
		checkBox_2.setCaption("CheckBox");
		checkBox_2.setImmediate(false);
		checkBox_2.setWidth("-1px");
		checkBox_2.setHeight("-1px");
		gridLayout_1.addComponent(checkBox_2, 1, 1);
		
		// label_3
		label_3 = new Label();
		label_3.setImmediate(false);
		label_3.setWidth("-1px");
		label_3.setHeight("-1px");
		label_3.setValue("Label");
		gridLayout_1.addComponent(label_3, 0, 2);
		
		// comboBox_1
		comboBox_1 = new ComboBox();
		comboBox_1.setImmediate(false);
		comboBox_1.setWidth("-1px");
		comboBox_1.setHeight("-1px");
		gridLayout_1.addComponent(comboBox_1, 1, 2);
		
		// label_4
		label_4 = new Label();
		label_4.setImmediate(false);
		label_4.setWidth("-1px");
		label_4.setHeight("-1px");
		label_4.setValue("Label");
		gridLayout_1.addComponent(label_4, 0, 3);
		
		// comboBox_2
		comboBox_2 = new ComboBox();
		comboBox_2.setImmediate(false);
		comboBox_2.setWidth("-1px");
		comboBox_2.setHeight("-1px");
		gridLayout_1.addComponent(comboBox_2, 1, 3);
		
		// label_5
		label_5 = new Label();
		label_5.setImmediate(false);
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue("Label");
		gridLayout_1.addComponent(label_5, 0, 4);
		
		// slider_1
		slider_1 = new Slider();
		slider_1.setImmediate(false);
		slider_1.setWidth("-1px");
		slider_1.setHeight("6px");
		gridLayout_1.addComponent(slider_1, 1, 4);
		
		// label_6
		label_6 = new Label();
		label_6.setImmediate(false);
		label_6.setWidth("-1px");
		label_6.setHeight("-1px");
		label_6.setValue("Label");
		gridLayout_1.addComponent(label_6, 0, 5);
		
		// nativeSelect_1
		nativeSelect_1 = new NativeSelect();
		nativeSelect_1.setImmediate(false);
		nativeSelect_1.setWidth("-1px");
		nativeSelect_1.setHeight("-1px");
		gridLayout_1.addComponent(nativeSelect_1, 1, 5);
		
		// label_7
		label_7 = new Label();
		label_7.setImmediate(false);
		label_7.setWidth("-1px");
		label_7.setHeight("-1px");
		label_7.setValue("Label");
		gridLayout_1.addComponent(label_7, 0, 6);
		
		// textField_1
		textField_1 = new TextField();
		textField_1.setImmediate(false);
		textField_1.setWidth("-1px");
		textField_1.setHeight("-1px");
		gridLayout_1.addComponent(textField_1, 1, 6);
		
		// label_8
		label_8 = new Label();
		label_8.setImmediate(false);
		label_8.setWidth("-1px");
		label_8.setHeight("-1px");
		label_8.setValue("Label");
		gridLayout_1.addComponent(label_8, 0, 7);
		
		// label_9
		label_9 = new Label();
		label_9.setImmediate(false);
		label_9.setWidth("-1px");
		label_9.setHeight("-1px");
		label_9.setValue("Label");
		gridLayout_1.addComponent(label_9, 0, 8);
		
		return gridLayout_1;
	}

}
