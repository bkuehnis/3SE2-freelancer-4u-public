package ch.zhaw.freelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.freelancer4u.model.Job;

public class PercentageVoucher implements Voucher {

    private int discount = 0;
    static String errorMessageGreaterZero = "Error: Discount value must be greater zero.";
    static String errorMessage50 = "Error: Discount value must less or equal 50.";

    // XXX Aufgabe 6a)
    public PercentageVoucher(int discount) {
        if (discount <= 0) {
            throw new RuntimeException(errorMessageGreaterZero);
        } else if (discount > 50) {
            throw new RuntimeException(errorMessage50);
        }
        this.discount = discount;
    }

    @Override
    public double getDiscount(List<Job> jobs) {
        var totalPrice = jobs.stream().mapToDouble(p -> p.getEarnings()).sum();
        return totalPrice * ((double) discount / 100);
    }

}
