package pizzashop.unittesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PaymentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

class PaymentServiceTest {

    @Mock
    public PaymentRepository paymentRepository;

    @InjectMocks
    public PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPayments() {
        Mockito.when(paymentRepository.getAll()).thenReturn(new ArrayList<>());

        assert 0 == paymentService.getPayments().size();

        Mockito.verify(paymentRepository).getAll();
        Mockito.verify(paymentRepository, times(1)).getAll();

        try {
            Mockito.verify(paymentRepository, never()).add(new Payment(1, PaymentType.Card, 100));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(0, paymentService.getPayments().size());
        assert 0 == paymentService.getPayments().size();
    }

    @Test
    void addPayment() {
        Payment payment1 = new Payment(1, PaymentType.Card, 100);
        Payment payment2 = new Payment(2, PaymentType.Cash, 200);
        Mockito.when(paymentRepository.getAll()).thenReturn(Arrays.asList(payment1));

        try {
            paymentService.addPayment(payment2.getTableNumber(), payment2.getType(), payment2.getAmount());
            Mockito.verify(paymentRepository, times(1)).add(payment2);

            Mockito.verify(paymentRepository, never()).getAll();

            assert true;
            assertEquals(1, paymentService.getPayments().size());
            assert 1 == paymentService.getPayments().size();

            Mockito.verify(paymentRepository, times(2)).getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
