package com.hoangth.upnp.device;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;


@UpnpService(
        serviceId = @UpnpServiceId("TVSwitchPower"),
        serviceType = @UpnpServiceType(value = "TVSwitchPower", version = 1)
)
public class TVSwitchPower {
	public TVScreenGUI gui = null;
   
    @UpnpStateVariable(defaultValue = "0", sendEvents = true)
    private boolean target = false;

    @UpnpStateVariable(defaultValue = "0", sendEvents = true)
    private boolean status = false;

    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = "NewTargetValue")
                          boolean newTargetValue) {
        target = newTargetValue;
        status = newTargetValue;
        System.out.println("Switch is: " + status);
        if (gui != null) gui.repaint();
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
    public boolean getTarget() {
        return target;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus() {
        return status;
    }

}
