package expensesharing;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DriverClass {

	public static void main(String[] args) {
		
		Map<String,User> users=new HashMap<>();
		User u1=new User("u1", "user1", "u1@gmail.com",1234567890L);
		User u2=new User("u2", "user2", "u2@gmail.com",1768978654L);
		User u3=new User("u3", "user3", "u3@gmail.com",1737430984L);
		User u4=new User("u4", "user4", "u4@gmail.com",7854875957L);
		
		users.put(u1.getUserId(), u1);
		users.put(u2.getUserId(), u2);
		users.put(u3.getUserId(), u3);
		users.put(u4.getUserId(), u4);
		
		
		
		
	Scanner sc=new Scanner(System.in);
	
	while(true) {
		String[] input=sc.nextLine().split(" ");
		
		
		if(input.length==1 && input[0].equals("SHOW")) {
			ShowBalances.show(users);
		} else if(input.length==2 && input[0].equals("SHOW")) {
			User user=users.get(input[1]);
			if(user!=null) {
				ShowBalances.show(user);
			} else {
				System.out.println("continue");
			}
		} else if(input[0].equals("EXPENSE")) {
			ExpenseObject expense=createExpenseObject(input,users);
			ExpenseSplitter splitter = ExpenseFactory.create(expense);
			splitter.split();
		}
	}
	}
private static ExpenseObject createExpenseObject(String input[],Map<String,User> users) {
	User paidBy =   users.get(input[1]) ;
    Double amountPaid = Double.parseDouble(input[2]);
    int split = Integer.parseInt(input[3]);
    List<User> paidToUserList = new ArrayList<>(split);


    for(int i=0;i<split;i++) {
        paidToUserList.add(users.get(input[4+i]));
    }

     ExpenseType type = ExpenseType.valueOf(input[4+split]);

    return new ExpenseObject(type, paidBy, amountPaid, split, paidToUserList,input);
}
}
