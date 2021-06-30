package expensesharing;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ShowBalances {
	
public static void show(Map<String,User> users) {
	Set<String> userIds=new HashSet<>();
	boolean flag=true;
	for(Entry<String,User> entry:users.entrySet()) {
		User user=entry.getValue();
		userIds.add(user.getUserId());
		for(Entry<String,Double> balance:user.getBalanceInfo().entrySet()) {
			if(!userIds.contains(balance.getKey())) {
				if(balance.getValue()>0) {
					flag=false;
					System.out.println(balance.getKey()+" owes "+" "+user.getUserId()+" "+balance.getValue());
				} else {
					flag=false;
					System.out.println(user.getUserId()+" owes "+" "+balance.getKey()+" "+-(balance.getValue()));
				}
			}
		}
	}
	
	if(flag) {
		System.out.println("no balances");
	}
}

public static void show(User user) {
	boolean flag=true;
	for(Entry<String, Double> entry:user.getBalanceInfo().entrySet()) {
		if(entry.getValue()>0) {
			flag=false;
			System.out.println(entry.getKey()+" owes "+" "+user.getUserId()+" "+entry.getValue());
		} else {
			flag=false;
			System.out.println(user.getUserId()+" owes "+" "+entry.getKey()+" "+-(entry.getValue()));
		}
	}
	
	if(flag) {
		System.out.println("no balances");
	}
}

}
