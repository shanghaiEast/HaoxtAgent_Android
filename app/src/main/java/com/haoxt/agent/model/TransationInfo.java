package com.haoxt.agent.model;

import java.io.Serializable;

/**
 * 交易信息
 */
public class TransationInfo implements Serializable {

	private static final long serialVersionUID = 5315168193766259101L;
	private String PINBLK;
	private String action;
	private String track2;
	private String track3;
	private String tratyp;
	private String DCdata;
	private String TXAMT;
	private String rateType;

	private String PAY_TYPE;
	private String CardNo;
	private String ICnumber;
	private String trmmodno;
	private String randomNum;
	private String bbpos;

	private String PERIOD;
	private String posId;
	
	private String orderNo;
	
	

	@Override
	public String toString() {
		String temp = "[PINBLK]=" + PINBLK + "\n[track2]=" + track2
				+ "[track3]=" + track3 + "[tratyp]=" + tratyp + "[DCdata]="
				+ DCdata + "[TXAMT]=" + TXAMT + "[PAY_TYPE]=" + PAY_TYPE
				+ "[CardNo]=" + CardNo + "[ICnumber]=" + ICnumber
				+ "\n[trmmodno]=" + trmmodno+"[randomNum]="+randomNum+"[bbpos]="+bbpos+"[posId]="+posId;
		return temp;
	}

	// private String ;
	// private String ;
	public String getPINBLK() {
		return PINBLK;
	}

	public void setPINBLK(String pINBLK) {
		PINBLK = pINBLK;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getTrack3() {
		return track3;
	}

	public void setTrack3(String track3) {
		this.track3 = track3;
	}

	public String getTratyp() {
		return tratyp;
	}

	public void setTratyp(String tratyp) {
		this.tratyp = tratyp;
	}

	public String getDCdata() {
		return DCdata;
	}

	public void setDCdata(String dCdata) {
		DCdata = dCdata;
	}

	public String getTXAMT() {
		return TXAMT;
	}

	public void setTXAMT(String tXAMT) {
		TXAMT = tXAMT;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getPAY_TYPE() {
		return PAY_TYPE;
	}

	public void setPAY_TYPE(String pAY_TYPE) {
		PAY_TYPE = pAY_TYPE;
	}

	public String getCardNo() {
		return CardNo;
	}

	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}

	public String getICnumber() {
		return ICnumber;
	}

	public void setICnumber(String iCnumber) {
		ICnumber = iCnumber;
	}

	public String getTrmmodno() {
		return trmmodno;
	}

	public void setTrmmodno(String trmmodno) {
		this.trmmodno = trmmodno;
	}

	public String getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(String randomNum) {
		this.randomNum = randomNum;
	}

	public String getBbpos() {
		return bbpos;
	}

	public void setBbpos(String bbpos) {
		this.bbpos = bbpos;
	}

	public String getPERIOD() {
		return PERIOD;
	}

	public void setPERIOD(String pERIOD) {
		PERIOD = pERIOD;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
