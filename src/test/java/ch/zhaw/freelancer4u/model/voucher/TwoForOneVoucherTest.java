package ch.zhaw.freelancer4u.model.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;

// XXX Aufgabe 5c)
public class TwoForOneVoucherTest {

    @Test
    public void testOtherJob() {
        var job1 = new Job("desc1", "detail desc", JobType.IMPLEMENT, 77.0);
        var job2 = new Job("desc2", "detail desc", JobType.REVIEW, 77.0);

        var voucher = new TwoForOneVoucher(JobType.IMPLEMENT);
        var jobs = new ArrayList<Job>();
        jobs.add(job1);
        jobs.add(job2);

        assertEquals(0, voucher.getDiscount(jobs), 0.01);
    }

    @Test
    public void testSameJob() {
        var job1 = new Job("desc1", "detail desc", JobType.TEST, 77.0);
        var job2 = new Job("desc2", "detail desc", JobType.TEST, 33.0);

        var voucher = new TwoForOneVoucher(JobType.TEST);
        var jobs = Arrays.asList(job1, job2);

        assertEquals(55, voucher.getDiscount(jobs), 0.01);
    }

    @Test
    public void testThreeJobs_sameType() {
        var job1 = new Job("desc1", "detail desc", JobType.REVIEW, 77.0);
        var job2 = new Job("desc2", "detail desc", JobType.REVIEW, 33.0);
        var job3 = new Job("desc3", "detail desc", JobType.REVIEW, 99.0);

        var voucher = new TwoForOneVoucher(JobType.REVIEW);
        var jobs = Arrays.asList(job1, job2, job3);

        assertEquals(104.5, voucher.getDiscount(jobs), 0.01);
    }

    @Test
    public void testThreeJobs_mixedType() {
        var job1 = new Job("desc1", "detail desc", JobType.REVIEW, 77.0);
        var job2 = new Job("desc2", "detail desc", JobType.REVIEW, 33.0);
        var job3 = new Job("desc3", "detail desc", JobType.TEST, 99.0);

        var voucher = new TwoForOneVoucher(JobType.REVIEW);
        var jobs = Arrays.asList(job1, job2, job3);

        assertEquals(55.0, voucher.getDiscount(jobs), 0.01);
    }

    @ParameterizedTest
    @CsvSource({ "0,0", "1,0", "2,77", "3,115.5", "4,154" })
    public void testMultipleJobs(ArgumentsAccessor argumentsAccessor) {
        var voucher = new TwoForOneVoucher(JobType.TEST);
        var job = new Job("desc", "detail desc", JobType.TEST, 77.0);
        var jobs = new ArrayList<Job>();
        for (var i = 0; i < argumentsAccessor.getInteger(0); i++) {
            jobs.add(job);
        }
        var earnings = argumentsAccessor.getDouble(1);
        assertEquals(earnings, voucher.getDiscount(jobs), 0.01);
    }
}