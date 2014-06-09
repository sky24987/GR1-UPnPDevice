package com.hoangth.upnp.device;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("TVVolumeControl"), serviceType = @UpnpServiceType(value = "TVVolumeControl", version = 1))
public class TVVolumeControl {
	public TVScreenGUI gui = null;

	@UpnpStateVariable(defaultValue = "0")
	private int volume = 1;

	@UpnpAction
	public void setVolume(
			@UpnpInputArgument(name = "NewVolumeValue") int newVolumeValue) {
		if (newVolumeValue >= 1 && newVolumeValue <= 5) {
			volume = newVolumeValue;
		} else if (newVolumeValue < 1) {
			volume = 1;
		} else {
			volume = 5;
		}
		if (volume == 0) {
			System.out.println("TV is: mute");
		} else {
			System.out.println("TV volume is: " + volume);
		}
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetVolumeValue"))
	public int getVolume() {
		return volume;
	}

}
