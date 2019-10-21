package com.haoxt.agent.util;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.whty.bluetoothsdk.util.Utils;
import com.whty.tymposapi.IDeviceDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * 天喻机具代理类
 */
public class TYDeviceDelegate implements IDeviceDelegate {

	private Handler handler;

	public TYDeviceDelegate(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void onConnectedDevice(boolean isSuccess) {
		if (isSuccess) {
			handler.obtainMessage(DeviceSharedMSG.CONNECTEDDEVICE_SUCCESS,
					DeviceUIMessage.connected_device_success).sendToTarget();
		} else {
			handler.obtainMessage(DeviceSharedMSG.CONNECTEDDEVICE_FAIL,
					DeviceUIMessage.connected_device_fail).sendToTarget();
		}
	}

	@Override
	public void onDisconnectedDevice(boolean isSuccess) {
		if (isSuccess) {
			handler.obtainMessage(DeviceSharedMSG.DISCONNECTEDDEVICE_SUCCESS,
					DeviceUIMessage.disconnected_device_success).sendToTarget();
		} else {
			handler.obtainMessage(DeviceSharedMSG.DISCONNECTEDDEVICE_FAIL,
					DeviceUIMessage.disconnected_device_fail).sendToTarget();
		}
	}

	@Override
	public void onUpdateWorkingKey(boolean[] isSuccess) {
		if (isSuccess != null) {
			handler.obtainMessage(
					DeviceSharedMSG.UPDATEWORKINGKEY_SUCCESS,
					DeviceUIMessage.update_TDK + " " + String.valueOf(isSuccess[0])
							+ "\n" + DeviceUIMessage.update_PIK + " "
							+ String.valueOf(isSuccess[1]) + "\n"
							+ DeviceUIMessage.update_MAK + " "
							+ String.valueOf(isSuccess[2])).sendToTarget();
		} else {
			handler.obtainMessage(DeviceSharedMSG.UPDATEWORKINGKEY_FAIL,
					DeviceUIMessage.update_working_key_fail).sendToTarget();
		}
	}

	@Override
	public void onGetMacWithMKIndex(byte[] data) {
		if (data != null) {
			handler.obtainMessage(DeviceSharedMSG.GETMACWITHMKINDEX_SUCCESS,
					Utils.bytesToHexString(data, data.length)).sendToTarget();
		} else {
			handler.obtainMessage(DeviceSharedMSG.GETMACWITHMKINDEX_FAIL,
					DeviceUIMessage.calculate_mac_fail).sendToTarget();
		}
	}

	@Override
	public void onReadCard(HashMap data) {
		if (data != null) {
			handler.obtainMessage(DeviceSharedMSG.READCARD_SUCCESS, data.toString())
					.sendToTarget();
		} else {
			handler.obtainMessage(DeviceSharedMSG.READCARD_FAIL, DeviceUIMessage.read_card_fail)
					.sendToTarget();
		}
	}

	@Override
	public void onReadCardData(HashMap hashMap) {   //小额免密
		Log.e("please_enter_password", hashMap.toString());
		if (hashMap != null && hashMap.get("errorCode").equals("9000")) {
			Map<String, String> newMap = new HashMap<>();
			newMap.put("pan", (String) hashMap.get("cardNumber"));
			String expiryDate = (String) hashMap.get("expiryDate");
			String date = expiryDate.substring(expiryDate.length() - 4, expiryDate.length());
			newMap.put("dateExpiration", date);
			newMap.put("ICSystemRelated", (String) hashMap.get("icData"));
			newMap.put("cardSequenceNumber", (String) hashMap.get("cardSeqNum"));
			newMap.put("track2", (String) hashMap.get("encTrack2Ex"));
//                newMap.put("pin", (String) hashMap.get("pin"));
			String cardType = (String) hashMap.get("cardType");
			if (cardType.equals("00")) {
				newMap.put("posEntryModeCode", "1");
			} else if (cardType.equals("01")) {
				newMap.put("posEntryModeCode", "2");
			} else if (cardType.equals("02")) {
				newMap.put("posEntryModeCode", "3");
			} else {
				newMap.put("posEntryModeCode", "");
			}

			String cardInfo = new Gson().toJson(newMap);
			handler.obtainMessage(DeviceSharedMSG.READCARDDATA_SUCCESS, cardInfo).sendToTarget();


		}else if(hashMap != null && hashMap.get("errorCode").equals("8005")){
			handler.obtainMessage(DeviceSharedMSG.READCARDDATA_SUCCESS, "交易取消").sendToTarget();
		}


	}

	@Override
	public void onGetPinBlock(HashMap hashMap) {
		Log.e("pinblock", hashMap.toString());
		if (hashMap != null && hashMap.get("errorCode").equals("9000")) {
		}
		String pin = (String) hashMap.get("pin");
		handler.obtainMessage(DeviceSharedMSG.GETPINBLOCK, pin).sendToTarget();

	}

	@Override
	public void onDownGradeTransaction(HashMap data) {
		handler.obtainMessage(DeviceSharedMSG.DOWNGRADETRANSACTION,
				DeviceUIMessage.downGrade_transaction).sendToTarget();
	}

	@Override
	public void onWaitingcard() {
		handler.obtainMessage(DeviceSharedMSG.WAITINGCARD, "请刷卡或插卡").sendToTarget();
	}

	@Override
	public void onHandleError(HashMap data) {
		// TODO 自动生成的方法存根
		Log.e("ErrorBroadcastReceiver", data.toString());
		handler.obtainMessage(DeviceSharedMSG.SHOW_MSG, data.toString())
				.sendToTarget();
	}

}
