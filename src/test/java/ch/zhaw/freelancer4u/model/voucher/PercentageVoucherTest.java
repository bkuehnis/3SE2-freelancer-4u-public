package ch.zhaw.freelancer4u.model.voucher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ch.zhaw.freelancer4u.model.Job;
import ch.zhaw.freelancer4u.model.JobType;

// XXX Aufgabe 5b)
public class PercentageVoucherTest {

    @Test
    public void testVoucher_withoutJobs() {
        var voucher = new PercentageVoucher(7);
        assertEquals(0, voucher.getDiscount(new ArrayList<Job>()), 0.01);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 5, 20, 49, 50 })
    public void testVoucher_singleJob_multipleValues(int discount) {
        var voucher = new PercentageVoucher(discount);
        var job = new Job("desc1", "detail desc", JobType.IMPLEMENT, 50.0);
        assertEquals(50*discount/100.0, voucher.getDiscount(Arrays.asList(job)), 0.01);
    }

    @Test
    public void testVoucher_withJobs() {
        var voucher = new PercentageVoucher(42);
        var job1 = new Job("desc1", "detail desc", JobType.IMPLEMENT, 77.0);
        var job2 = new Job("desc2", "detail desc", JobType.IMPLEMENT, 42.0);

        assertEquals(49.98, voucher.getDiscount(Arrays.asList(job1, job2)), 0.01);
    }

    // XXX Aufgabe 6c)
    @Test
    public void testVoucher_withJobs_Mock() {
        var voucher = new PercentageVoucher(42);

        var job1 = mock(Job.class);
        var job2 = mock(Job.class);
        when(job1.getEarnings()).thenReturn(77.0);
        when(job2.getEarnings()).thenReturn(42.0);

        assertEquals(49.98, voucher.getDiscount(Arrays.asList(job1, job2)), 0.01);
    }

    // XXX Aufgabe 6b)
    @Test
    public void testVoucher_belowOrEqualZero() {
        var exception1 = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(0);
        });
        assertEquals(PercentageVoucher.errorMessageGreaterZero, exception1.getMessage());
        var exception2 = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(-5);
        });
        assertEquals(PercentageVoucher.errorMessageGreaterZero, exception2.getMessage());
    }

    // XXX Aufgabe 6b)
    @Test
    public void testVoucher_greater50() {
        var exception1 = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(51);
        });
        assertEquals(PercentageVoucher.errorMessage50, exception1.getMessage());

        var exception2 = assertThrows(RuntimeException.class, () -> {
            new PercentageVoucher(120);
        });
        assertEquals(PercentageVoucher.errorMessage50, exception2.getMessage());
    }
}