package cn.itcast.erp.pojo;

import java.util.Date;

/**
 * 
 * 说明：实体类基类
 * @author bigApple
 * @version 1.0
 * @date 2017年2月17日
 */
public class BaseEntity {
	
	//创建人
	private String createBy;
	//创建部门
	private String createDept;
	//创建事件
	private Date createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Date updateTime;
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "BaseEntity [createBy=" + createBy + ", createDept="
				+ createDept + ", creatTime=" + createTime + ", updateBy="
				+ updateBy + ", updateTime=" + updateTime + "]";
	}
	
	
	
}
