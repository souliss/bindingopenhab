# openHAB Native Binding for Souliss #

## Introduction ##

OpenHAB is a vendor and technology agnostic open source automation software. 
The code for networking activities of this binding is based on [AndroidApplication SoulissApp] code. It use the same approach getting from the gateway node the network structure and the relevant data. You can use [AndroidApplication SoulissApp] and the [openHAB_native openHAB binding] binding at same time.

## General Configuration Requirements ##

### Prerequisites ###

In order to use this binding a little knowledge of openHAB is required because it offers services on top of the openHAB platform so you have to deal with its characteristics and requirements.
The best is start with the http://www.openhab.org/gettingstarted.html and https://github.com/openhab/openhab/wiki openHAB wiki.
Once done, use the demo configuration only to ensure that your openHAB is working fine, then delete it and create your own configuration as follow.

### Overview ###

A minimum openHAB configuration requires fours files:
 * The Souliss binding for openHAB (is a Java plugin)
 * The binding network configuration
 * The .items file to define the items (also known as Typicals in Souliss)
 * The .sitemap file to define how the user interface will look like

As main difference while using [AndroidApplication SoulissApp] is that you have to define your user interface, declaring how many Typicals (Items in openHAB) you have and which is their purpose. [AndroidApplication SoulissApp] is rather able to collect those information automatically.

### Binding Network Configuration ###

The openHAB binding isn't able to locate your Souliss gateway as Android Application SoulissApp does, so you should specify the networking information in the configuration file as follow.

Edit *.\openhab\configurations\openhab.cfg*

Add at the bottom:
```
################################ Souliss Binding ##########################################
#
# Souliss
#
# For ITEM defination in file .item
#{souliss=<Typical>:<nodeID>:<slot>:[<bit>]}
#{souliss=<Typical>:<nodeID>:<slot>:[<useslot>]}
souliss:IP_LAN=192.168.1.105
souliss:USER_INDEX=71
souliss:NODE_INDEX=134

#SERVERPORT - Leave empty for casual port
souliss:SERVERPORT=

#time in mills - min 50
souliss:REFRESH_DBSTRUCT_TIME=600000
souliss:REFRESH_SUBSCRIPTION_TIME=120000
souliss:REFRESH_HEALTY_TIME=60000
souliss:REFRESH_MONITOR_TIME=500
souliss:SEND_DELAY=1500
souliss:SEND_MIN_DELAY=100
souliss:SECURE_SEND_TIMEOUT_TO_REQUEUE=5000
souliss:SECURE_SEND_TIMEOUT_TO_REMOVE_PACKET=30000
###########################################################################################
```
The following parameters need to be changed:

```
 Parameter   |            Description                    |   Range
-------------+-------------------------------------------+----------+
 IP_LAN      | The IP address of Souliss Gateway node    |    -     |
 USER_INDEX  | Identify the index of the openHAB binding |  1 - 100 |
 NODE_NUMBER | Identify the node of the openHAB binding  |  1 - 254 |
-------------+-------------------------------------------+----------+
```

Please ensure that each user interface (SoulissApp, openHAB, ...) must have a different USER_INDEX and NODE_NUMBER, those values are used to identify the interface.
You can get the values used by !SoulissApp from the Networking menu.

NODE_NUMBERS has to be set as the number of nodes that build your souliss network; if your network is composed by 7 nodes (the App lists them as node0-node6) sets it at 7.

### Configure .items file ###

Define your Souliss deployment inside openHAB. To do this, you'll have to statically _map_ your device inside openHAB environment.

Edit *.\openhab\configurations\items\defaultSitemap.items*

define your own items and add to the end of each line of definition

 ```
 {souliss=<Typical>:<nodeID>:<slot>:[<bit>]}
 ```

where Typicals can be T11, T12, T13, T16, T1A, T22, T31, T51, T52, T53, T57, D98, D99 (more will be supported in the future)

```
 Parameter   |            Description                                               | Range         |
-------------+----------------------------------------------------------------------+---------------+
 nodeID      | Is the ID of the node, the first node listed in your !SoulissApp 	| 1 - 254       |
 			 |    has nodeID 1, increase for your further nodes    				    |               |
 slot        | Is the slot where your Typical is located, this is defined in 		| 1 - 254       |
			 |    the sketch loaded in your node 									| 		        |
 bit         | Is used only for Typicals that works bitwise as T1A                  |  1 - 8        |
 useOfSlot   | Is used only for Typicals that works as T3					        | heating       |
																		    	"   | cooling       |
																				"   | measured      |
																				"   | settpoint     |
																				"   | setasmeasured |
																				"   | heatingcooling|
																				"   | fanoff        |
																				"   | fanautomode   |
																				"   | fanhight      |
																				"   | fanmed        |
																				"   | fanlow        |
																				"   | shutdown		|
-------------+----------------------------------------------------------------------+---------------+

```

An example of the *.items* configuration files is below, consider that openHAB has its own syntax for the configuration files.

```
Switch LuceSogg	"LuceSogg" (GF_Soggiorno, TechnicView_Node0) {souliss="T11:0:6", autoupdate=false}
Number Temperature_GF_Soggiorno "Temperatura Soggiorno [%.1f °C]"	<temperature> (temperature, GF_Temperature, TechnicView_Node0) {souliss="T52:0:0", autoupdate=false}
Number Umidita_GF_Soggiorno "Umidità Soggiorno [%.1f %%]"	<temperature> (temperature, GF_Temperature, TechnicView_Node0) {souliss="T53:0:2", autoupdate=false}
Number Consumo_GF_Soggiorno "Consumo [%.1f W]" <energy>	(GF_Temperature, TechnicView_Node0) {souliss="T57:0:4", autoupdate=false}
Rollershutter Shutter1_GF_Soggiorno "Tenda1" (GF_Soggiorno, TechnicView_Node3 {souliss="T22:3:0", autoupdate=false} 
Contact ContattoTest "Contatto" (GF_Soggiorno, Lights) {souliss="T13:0:1", autoupdate=true}
Contact C2 "BIT 3" (GF_Soggiorno, TechnicView_Node3) {souliss="T1A:3:5:2"}
Contact C3 "BIT 4" (GF_Soggiorno, TechnicView_Node3) {souliss="T1A:3:5:3"}
Color  RGB_Led_Strip_1 "RGB Led Strip 1" <slider> (GF_Soggiorno)  {souliss="T16:0:2", autoupdate=false}

Number Temperature_GF_Soggiorno 	"Temperature [%.1f °C]"	<temperature> (T31, TechnicView_Node0) {souliss="T31:1:0:measured"}
Number Temperature_2F_Living_SP	"Temp Set Point [%.1f °C]" 	<temperature> (T31, TechnicView_Node0) {souliss="T31:1:0:setpoint"}
Switch setasmeasured "Set as measured" <temperature> (T31, TechnicView_Node0) {souliss="T31:1:0:setasmeasured"}
Switch heating_cooling "Heating Mode" (T31, TechnicView_Node0) {souliss="T31:1:0:heatingcooling", autoupdate="false"}
Contact heating "Heating" <siren>  (T31, TechnicView_Node0) {souliss="T31:1:0:heating"}
Contact cooling "Cooling" <siren> (T31, TechnicView_Node0) {souliss="T31:1:0:cooling"}
Switch fan_off "Fan Off" (T31, TechnicView_Node0) {souliss="T31:1:0:fanoff", autoupdate="false"}
Switch fan_low "Fan Low" (T31, TechnicView_Node0) {souliss="T31:1:0:fanlow", autoupdate="false"}
Switch fan_med "Fan Med" (T31, TechnicView_Node0) {souliss="T31:1:0:fanmed", autoupdate="false"}
Switch fan_high "Fan High" (T31, TechnicView_Node0) {souliss="T31:1:0:fanhigh", autoupdate="false"}
Switch fan_auto_mode "Fan Auto Mode" (T31, TechnicView_Node0) {souliss="T31:1:0:fanautomode", autoupdate="false"}
Switch shutdown "Shutdown" (T31, TechnicView_Node0) {souliss="T31:1:0:shutdown"}
```

### Service Typicals  ###

In order to track the health and timestamps two service Typicals has been introduced: D98 (Health) e D99 (Timestamp), those are not defined in Souliss sketches and are only available on the binding.

Use them as :

```
{souliss="D98:<nodeNumber>:998"}
{souliss="D99:<nodeNumber>:999"}
```

Example:

```
Number HEALTH_Node1_GF_Service "Health Node 1 [%1d]" <keyring> (Diagnostic, TechnicView_Node0) {souliss="D98:0:998"}

String TIMESTAMP_Node1_GF_Service "Timestamp Node 1 [%1$td.%1$tm.%1$tY %1$tk:%1$tM:%1$tS]" <keyring> (Diagnostic, TechnicView_Node0) {souliss="D99:0:999"}
```

### Download Binding Java Binary File ###

The binding is a Java plugin that you should place in *.\openhab\addons* folder. Is actually available only from our [Download] page.

### Restart openHAB ###

At this time you can restart your openHAB runtime, then the integrated web server will be available and you should be able to control your Souliss nodes through it.

Using a WebKit enable browser (like Chrome or Firefox) open the link
```
http://openHAB_IP_ADDRESS:8080/openhab.app?sitemap=YOUR_SITEMAP_NAME
```

If the browser is located on the same machine where you are running the openHAB runtime, you can use 127.0.0.1 as openHAB_IP_ADDRESS.
