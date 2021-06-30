package expensesharing;

import java.util.List;

public class ExactSplitter implements ExpenseSplitter{
	private User paidBy;
	private List<User> paidTo;
	private List<Double> sharePerPerson;
	private Double amountPaid;

	public ExactSplitter(User paidBy, List<User> paidTo, List<Double> sharePerPerson,Double amountPaid) {
		super();
		this.paidBy = paidBy;
		this.paidTo = paidTo;
		this.sharePerPerson = sharePerPerson;
		this.amountPaid=amountPaid;
	}

	@Override
	public void split() {
		if(checkShareVsAmount(amountPaid, sharePerPerson)) {
			return;
		}

		for(int i=0;i<paidTo.size();i++) {
			User user=paidTo.get(i);
			if(user==paidBy) {
				continue;
			}
			Double amountPaid=sharePerPerson.get(i);
			Double balance=paidBy.getBalanceInfo().get(user.getUserId());
			if(balance==null) {

				paidBy.getBalanceInfo().put(user.getUserId(), new Double(amountPaid));
				user.getBalanceInfo().put(paidBy.getUserId(), new Double(-amountPaid));
			} else {
			if(balance+amountPaid==0.00) {
				paidBy.getBalanceInfo().remove(user.getUserId());
				user.getBalanceInfo().remove(paidBy.getUserId());
			} else {
				paidBy.getBalanceInfo().put(user.getUserId(), balance+amountPaid);
				user.getBalanceInfo().put(paidBy.getUserId(), -(balance+amountPaid));
			}
			}
		}

	}
	private boolean checkShareVsAmount(Double amountPaid,List<Double> sharePerPerson) {

		double total=0.0;
		for(Double amount:sharePerPerson) {
			total+=amount;
		}

		if(total!=amountPaid) {
			System.out.println("Share per person dosen't sum up to total amount");
			return true;
		}

		return false;
	}
}
