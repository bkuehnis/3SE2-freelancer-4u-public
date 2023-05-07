package ch.zhaw.freelancer4u.model.voucher;

import java.util.List;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;

public class TwoForOneVoucher implements Voucher {

    JobType jobType;

    public TwoForOneVoucher(JobType jobType) {
        this.jobType = jobType;
    }

    @Override
    public double getDiscount(List<Job> jobs) {
        var filteredJobs = jobs.stream().filter(p -> this.jobType.equals(p.getJobType())).toList();
        var sum = filteredJobs.stream().mapToDouble(p -> p.getEarnings()).sum();
        if (filteredJobs.size() <=1) {
            return 0;
        }
        return sum / 2;
    }

}
