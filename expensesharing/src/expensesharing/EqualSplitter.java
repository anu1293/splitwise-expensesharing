package expensesharing;

import java.util.List;

public class EqualSplitter implements ExpenseSplitter {
	private User paidBy;
	private List<User> paidTo;
	private Double amountPaid;

	public EqualSplitter(User paidBy, List<User> paidTo, Double amountPaid) {
		super();
		this.paidBy = paidBy;
		this.paidTo = paidTo;
		this.amountPaid = amountPaid;
	}


	@Override
	public void split() {
		
		Double splitValue=round(amountPaid/(paidTo.size()),2);
		
		for(User user:paidTo) {
			
			if(user==paidBy) {
				continue;
			}
			Double balance=paidBy.getBalanceInfo().get(user.getUserId());
			if(balance==null) {
				
				paidBy.getBalanceInfo().put(user.getUserId(), new Double(splitValue));
				user.getBalanceInfo().put(paidBy.getUserId(), new Double(-splitValue));
			} else {
			if(balance+splitValue==0.00) {
				paidBy.getBalanceInfo().remove(user.getUserId());
				user.getBalanceInfo().remove(paidBy.getUserId());
			} else {
				paidBy.getBalanceInfo().put(user.getUserId(), balance+splitValue);
				user.getBalanceInfo().put(paidBy.getUserId(), -(balance+splitValue));
			}
			}
		}

	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
