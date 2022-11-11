package com.oronberg.vivelink;

import net.blf02.vrapi.api.IVRAPI;
import net.blf02.vrapi.api.VRAPIPlugin;
import net.blf02.vrapi.api.VRAPIPluginProvider;

@VRAPIPlugin
public class VRPlugin implements VRAPIPluginProvider {

    public static IVRAPI vrAPI = null;

    @Override
    public void getVRAPI(IVRAPI ivrapi) {
        vrAPI = ivrapi;
        VRPluginStatus.hasPlugin = true;
    }
}