package ch.zhaw.freelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.freelancer4u.model.Job;

public interface Voucher {

    public double getDiscount(List<Job> jobs);

}
