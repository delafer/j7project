package org.delafer.xmlbench.wizard.buttons;

public class DynamicBtnData extends AbstractBtnData {

	public DynamicBtnData(String name, int uid) {
		super(name, uid);
	}

	public DynamicBtnData(int uid) {
		super(uid);
	}

	public Type getType() {
		return Type.DYNAMIC;
	}

}
