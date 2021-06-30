package expensesharing;

import java.util.List;

public class ExpenseObject {
	ExpenseType type;
    User paidBy;
    double amountPaid;
    int splitBetween;
    List<User> paidToUserList;	   
    String[] input;
    
	 public ExpenseObject(ExpenseType type, User paidBy, double amountPaid, int splitBetween,
			List<User> paidToUserList, String[] input) {
		super();
		this.type = type;
		this.paidBy = paidBy;
		this.amountPaid = amountPaid;
		this.splitBetween = splitBetween;
		this.paidToUserList = paidToUserList;
		this.input = input;
	}
	
}
