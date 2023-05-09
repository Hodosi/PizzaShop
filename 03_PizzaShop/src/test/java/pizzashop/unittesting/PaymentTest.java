package pizzashop.unittesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PaymentTest {

    @Mock
    public Payment payment;

    @BeforeEach
    public void setUp() {
        payment = mock(Payment.class);
    }

    @Test
    void getType() {
        Mockito.when(payment.getType()).thenReturn(PaymentType.Cash);
        assertEquals(PaymentType.Cash, payment.getType());
    }

    @Test
    void getTableNumber() {
        Mockito.when(payment.getTableNumber()).thenReturn(1);
        assertEquals(1, payment.getTableNumber());
    }
}
