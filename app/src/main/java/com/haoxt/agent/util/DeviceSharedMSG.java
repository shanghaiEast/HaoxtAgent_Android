package com.haoxt.agent.util;

public class DeviceSharedMSG {

	// 外部状态
	public final static int CONNECTEDDEVICE_SUCCESS = 0;
	public final static int CONNECTEDDEVICE_FAIL = 1;
	public final static int DISCONNECTEDDEVICE_SUCCESS = 3;
	public final static int DISCONNECTEDDEVICE_FAIL = 4;
	public final static int UPDATEWORKINGKEY_SUCCESS = 5;
	public final static int UPDATEWORKINGKEY_FAIL = 6;
	public final static int GETMACWITHMKINDEX_SUCCESS = 7;
	public final static int GETMACWITHMKINDEX_FAIL = 8;
	public final static int READCARD_SUCCESS = 9;
	public final static int	READCARD_FAIL = 10;
	public final static int READCARDDATA_SUCCESS = 11;
	public final static int READCARDDATA_FAIL = 12;
	public final static int DOWNGRADETRANSACTION = 13;
	public final static int WAITINGCARD = 14;
	public final static int GETPINBLOCK = 15;

	//内部状态
	public final static int GETDEVICESN = 16;
	public final static int GETDEVICEINFO = 17;
	public final static int GETFIELD59 = 18;
	public final static int UPDATEAPPTRANS = 19;
	public final static int DEVICESIGN = 20;
	public final static int DEVICESIGNDATA = 21;
	public final static int DEVICESIGNSUCCESS = 22;
	public final static int GETDEVICEKSN = 23;
	public final static int STARTREADCARD = 24;
	public final static int UPLOADTRANSATIONINFO = 25;
	public final static int GETPINBLOCKSATRT = 26;
	public final static int UPLOADTRANSATIONINFOSUCESS = 27;
	public final static int UPLOADDEVICEINFOSUCCESS = 28;


	public final static int SHOW_MSG = 31;
	public final static int SHOW_STATUS = 32;
	
	public final static int No_Device_Selected = 95;
	public final static int Device_Found = 97;
	public final static int No_Device = 98;
	public final static int Device_Ensured = 99;
	public final static int Device_Disconnected = 100;

}
