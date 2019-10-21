package com.haoxt.agent.model;

import java.io.Serializable;

/**用户类
 * @author baowen
 */
public class ItemCardInfo implements Serializable {


	public long id;
	private String TM_SMP;
	private String ACCT_NAME;
	private String OWN;
	private String ACCT_PHONE;
	private String ACCT_NO;
	private String CHECK_CHANNEL;
	private String ROW_ID;

	public String getTM_SMP() {
		return TM_SMP;
	}

	public void setTM_SMP(String TM_SMP) {
		this.TM_SMP = TM_SMP;
	}

	public String getACCT_NAME() {
		return ACCT_NAME;
	}

	public void setACCT_NAME(String ACCT_NAME) {
		this.ACCT_NAME = ACCT_NAME;
	}

	public String getOWN() {
		return OWN;
	}

	public void setOWN(String OWN) {
		this.OWN = OWN;
	}

	public String getACCT_PHONE() {
		return ACCT_PHONE;
	}

	public void setACCT_PHONE(String ACCT_PHONE) {
		this.ACCT_PHONE = ACCT_PHONE;
	}

	public String getACCT_NO() {
		return ACCT_NO;
	}

	public void setACCT_NO(String ACCT_NO) {
		this.ACCT_NO = ACCT_NO;
	}

	public String getCHECK_CHANNEL() {
		return CHECK_CHANNEL;
	}

	public void setCHECK_CHANNEL(String CHECK_CHANNEL) {
		this.CHECK_CHANNEL = CHECK_CHANNEL;
	}

	public String getROW_ID() {
		return ROW_ID;
	}

	public void setROW_ID(String ROW_ID) {
		this.ROW_ID = ROW_ID;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
