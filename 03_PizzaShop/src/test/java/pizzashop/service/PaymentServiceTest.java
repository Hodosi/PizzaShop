package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    PaymentService paymentService;
    final String filename = "data/testPayments.txt";

    @BeforeEach
    void setUp() {
        PaymentRepository paymentRepository = new PaymentRepository(filename);
        paymentService = new PaymentService(null, paymentRepository);
    }

    @Test
    void test_F02_TC01() {
        double totalPaymentAmount = paymentService.getTotalAmount(PaymentType.Cash);
        assertEquals(0.0, totalPaymentAmount);
    }

    @Test
    void test_F02_TC02() {
        try {
            paymentService.addPayment(1, PaymentType.Cash, 1);
            paymentService.addPayment(2, PaymentType.Card, 2);
            paymentService.addPayment(3, PaymentType.Cash, 3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        double totalPaymentAmount = paymentService.getTotalAmount(PaymentType.Cash);

        assertEquals(4, totalPaymentAmount);
    }

    @Test
    void test_F02_TC03() {
        PaymentRepository paymentRepository = new PaymentRepository(true);
        paymentService = new PaymentService(null, paymentRepository);
        double totalPaymentAmount = paymentService.getTotalAmount(PaymentType.Card);
        assertEquals(0.0, totalPaymentAmount);
    }

    @Test
    void test_F02_TC04() {
        try {
            paymentService.addPayment(1, PaymentType.Card, 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double totalPaymentAmount = paymentService.getTotalAmount(PaymentType.Cash);
        assertEquals(0.0, totalPaymentAmount);
    }

    @Test
    void test_F02_TC05() {
        final Payment payment = new Payment(1, PaymentType.Cash, -15);
        PaymentRepository paymentRepository = new PaymentRepository(List.of(payment));
        paymentService = new PaymentService(null, paymentRepository);

        try {
            double totalPaymentAmount = paymentService.getTotalAmount(PaymentType.Cash);
            assertEquals(-15.0, totalPaymentAmount);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}
