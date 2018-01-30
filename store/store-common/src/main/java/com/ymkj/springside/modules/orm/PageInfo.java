package com.ymkj.springside.modules.orm;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.ymkj.springside.modules.utils.ToStringBuilder;

public class PageInfo<T> implements Serializable{

	private static final long serialVersionUID = 5704194201577728928L;
	
	private int pageNo = 1;//页码，默认是第一页  
    private int pageSize = 20;//每页显示的记录数，默认是20  
    private int totalRecord;//总记录数  
    private int totalPage;//总页数  
    private Object queryParam;
    private List<T> results = Lists.newArrayList();//对应的当前页记录
    
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
	public String toString() {
		return ToStringBuilder.build(this);
	}

	public Object getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(Object queryParam) {
		this.queryParam = queryParam;
	}
	
	
	/*
	 * 手机号
	 */
	private String mobilePhone;
	/*
	 * 推荐人
	 */
	private String referalName;
	/*
	 * 客户等级
	 */
	private String customerLevel;
	/*
	 * 客户类型
	 */
	private String cellType;

	/*
	 * 已注资人数
	 */
	private String inviteNum;

	/*
	 * 累计万元
	 */
	private String totalInvest;
	/*
	 * 客户的邀请码
	 */
	private String customerInviteCode;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getReferalName() {
		return referalName;
	}

	public void setReferalName(String referalName) {
		this.referalName = referalName;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCellType() {
		return cellType;
	}

	public void setCellType(String cellType) {
		this.cellType = cellType;
	}

	public String getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(String inviteNum) {
		this.inviteNum = inviteNum;
	}

	public String getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(String totalInvest) {
		this.totalInvest = totalInvest;
	}

	public String getCustomerInviteCode() {
		return customerInviteCode;
	}

	public void setCustomerInviteCode(String customerInviteCode) {
		this.customerInviteCode = customerInviteCode;
	}
	

}  