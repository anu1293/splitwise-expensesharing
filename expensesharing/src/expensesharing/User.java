package expensesharing;

import java.util.HashMap;
import java.util.Map;

public class User {

	private String userId,name,email;
	private Long mobNo;
	Map<String,Double> balanceInfo;

	public User(String userId, String name, String email, Long mobNo) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.mobNo = mobNo;
		this.balanceInfo=new HashMap<>();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobNo() {
		return mobNo;
	}
	public void setMobNo(Long mobNo) {
		this.mobNo = mobNo;
	}
	public Map<String, Double> getBalanceInfo() {
		return balanceInfo;
	}
	public void setBalanceInfo(Map<String, Double> balanceInfo) {
		this.balanceInfo = balanceInfo;
	}


}
