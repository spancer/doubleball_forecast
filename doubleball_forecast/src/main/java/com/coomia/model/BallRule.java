package com.coomia.model;

//杀球规则类
public  class BallRule
{
	int id;
	String rule;
	String desc;
	String ballType = "kill-red";
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBallType() {
		return ballType;
	}
	public void setBallType(String ballType) {
		this.ballType = ballType;
	}
	public BallRule(String rule, String desc, String ballType) {
		super();
		this.rule = rule;
		this.desc = desc;
		this.ballType = ballType;
	}
	public BallRule(String rule, String ballType) {
		super();
		this.rule = rule;
		this.ballType = ballType;
	}
	public BallRule() {
		super();
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {
		if(o == null)
        {
            return false;
        }
        if (o == this)
        {
           return true;
        }
        if (getClass() != o.getClass())
        {
            return false;
        }
        BallRule b = (BallRule) o;
        return (this.getBallType().equals(b.getBallType()) && this.getRule().equals(b.getRule()) && this.getDesc().equals(b.getDesc()) && this.getId() == b.getId());
	}
	
	@Override
	public int hashCode() {
		 return this.getBallType().hashCode()+this.getDesc().hashCode()*2 + this.getRule().hashCode()*3 +id;
	}
	
}