package com.hoangth.upnp.device;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("TVChannelControl"), serviceType = @UpnpServiceType(value = "TVChannelControl", version = 1))
public class TVChannelControl {
	public TVScreenGUI gui = null;
	
	@UpnpStateVariable(defaultValue = "1")
	private int channel = 1;
	
	@UpnpAction
	public void setChannel(
			@UpnpInputArgument(name = "NewChannelValue") int newChannelValue) {
		if (newChannelValue >= 1 && newChannelValue <= 99) {
			channel = newChannelValue;
		} else if (newChannelValue < 1) {
			channel = 1;
		} else {
			channel = 99;
		}
		System.out.println("TV channel is: " + channel);
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetChannelValue"))
	public int getChannel() {
		return channel;
	}
}
