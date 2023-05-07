package ch.zhaw.freelancer4u.model.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;

// XXX Aufgabe 5a)
public class FiveBucksVoucherTest {

    @Test
    public void testEmpty() {
        var voucher = new FiveBucksVoucher();
        assertEquals(0, voucher.getDiscount(new ArrayList<Job>()), 0.01);
    }

    @Test
    public void testTwo() {
        var jobs = new ArrayList<Job>();
        var voucher = new FiveBucksVoucher();
        jobs.add(new Job("desc", "detail desc", JobType.IMPLEMENT, 2.0));
        assertEquals(0, voucher.getDiscount(new ArrayList<Job>()), 0.01);
    }

    @Test
    public void testTen() {
        var voucher = new FiveBucksVoucher();
        var jobs = new ArrayList<Job>();
        jobs.add(new Job("description", "detail desc", JobType.IMPLEMENT, 10.0));
        assertEquals(5, voucher.getDiscount(jobs), 0.01);
    }

}
