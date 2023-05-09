package pizzashop.unittesting;

import org.junit.jupiter.api.*;
import org.mockito.*;
import pizzashop.model.Payment;
import pizzashop.repository.PaymentRepositoryInMemory;
import pizzashop.repository.PaymentValidator;

import java.util.List;

import static org.mockito.Mockito.*;

class PaymentRepositoryTest {

    @InjectMocks
    private PaymentRepositoryInMemory paymentRepository;

    @Mock
    private PaymentValidator validator;

    Payment payment;

    List<Payment> paymentList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        payment = mock(Payment.class);
    }

    @Test
    public void add() {
        assert 2 == paymentRepository.getAll().size();
        Mockito.when(validator.validatePayment(payment)).thenReturn("EROARE GRAVA/GROASA");
        try {
            paymentRepository.add(payment);
            assert false;
        } catch (Exception e) {
            assert e.getMessage().equals("EROARE GRAVA/GROASA");
        }
        Mockito.verify(validator, times(1)).validatePayment(payment);
    }

    @Test
    public void setList() {
        Mockito.when(validator.validatePaymentList(paymentList)).thenReturn("corrupted list");
        try {
            paymentRepository.setList(paymentList);
            assert false;
        } catch (Exception e) {
            assert e.getMessage().equals("corrupted list");
        }
        Mockito.verify(validator, times(1)).validatePaymentList(paymentList);
    }

    @AfterEach
    public void tearDown() {
        paymentRepository = null;
        payment = null;
    }
}