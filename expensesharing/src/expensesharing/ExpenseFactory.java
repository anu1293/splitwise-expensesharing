package expensesharing;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFactory {
	public static ExpenseSplitter create(ExpenseObject expense) {

		switch(expense.type) {

		case EQUAL: {return new EqualSplitter(expense.paidBy, expense.paidToUserList, expense.amountPaid);}
		case EXACT: {
			List<Double> amountPerPerson = new ArrayList<>(expense.splitBetween);
			for(int i=0;i<expense.splitBetween;i++) {
				amountPerPerson.add(Double.parseDouble(expense.input[4+expense.splitBetween+1 + i]));
			}
			return new ExactSplitter(expense.paidBy, expense.paidToUserList, amountPerPerson, expense.amountPaid);
		}
		default :return null;
		}
	}

}
