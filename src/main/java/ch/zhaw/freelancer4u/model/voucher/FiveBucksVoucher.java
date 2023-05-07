package ch.zhaw.freelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.freelancer4u.model.Job;

public class FiveBucksVoucher implements Voucher {

    @Override
    public double getDiscount(List<Job> jobs) {
        var sum = jobs.stream().mapToDouble(p -> p.getEarnings()).sum();
        if (sum >= 10) {
            return 5;
        }
        return 0;
    }

}
