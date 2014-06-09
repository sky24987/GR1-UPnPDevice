package com.hoangth.upnp.device;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("TVVolumnControl"), serviceType = @UpnpServiceType(value = "TVVolumnControl", version = 1))
public class TVVolumnControl {
	public TVScreenGUI gui = null;

	@UpnpStateVariable(defaultValue = "1")
	private int volumn = 1;

	@UpnpAction
	public void setVolumn(
			@UpnpInputArgument(name = "NewVolumnValue") int newVolumnValue) {
		if (newVolumnValue >= 1 && newVolumnValue <= 5) {
			volumn = newVolumnValue;
		} else if (newVolumnValue < 1) {
			volumn = 1;
		} else {
			volumn = 5;
		}
		if (volumn == 0) {
			System.out.println("TV is: mute");
		} else {
			System.out.println("TV volumn is: " + volumn);
		}
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetVolumnValue"))
	public int getVolumn() {
		return volumn;
	}

}
