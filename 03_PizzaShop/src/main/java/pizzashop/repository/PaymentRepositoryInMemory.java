package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.ArrayList;
import java.util.List;


public class PaymentRepositoryInMemory {
    private List<Payment> paymentList;
    private PaymentValidator validator;

    public PaymentRepositoryInMemory(PaymentValidator validator) {
        this.validator = validator;
        this.paymentList = List.of(new Payment(1, PaymentType.Cash, 100), new Payment(2, PaymentType.Card, 250));
    }

    public void add(Payment payment) {
        final String error = validator.validatePayment(payment);
        if (error == null) {
            throw new RuntimeException("eroare null");
        }

        if (error.length() > 0) {
            throw new RuntimeException(error);
        }

        this.paymentList.add(payment);
    }

    public List<Payment> getAll() {
        return this.paymentList;
    }

    public void setList(List<Payment> paymentList){
        final String error = validator.validatePaymentList(paymentList);
        if (error == null) {
            throw new RuntimeException("eroare null");
        }

        if (error.length() > 0) {
            throw new RuntimeException(error);
        }
        this.paymentList = paymentList;
    }
}


//ublic class RepositoryWithMockTest {
//
//    @InjectMocks
//    private RepositoryInMemory repo;
//
//    @Mock
//    private QuizValidator validator;
//
//    private Quiz quiz;
//
////    @BeforeEach
////    public void setUp(){
////
////        MockitoAnnotations.initMocks(this);
//////        try {
//////            repo = new RepositoryInMemory(validator);
//////        } catch (RepositoryException e) {
//////            e.printStackTrace();
//////        }
////        quiz=mock(Quiz.class);
////    }
////
////    @Test
////    public void add_validQuiz(){
////        assert 5 == repo.getAll().size();
////        System.out.println(repo.getAll().toString());
////        Mockito.when(validator.validate(quiz)).thenReturn(new ArrayList<String>(Collections.singleton("abc")));
////
////        try {
////            repo.add(quiz);
////        } catch (RepositoryException e) {
////            e.printStackTrace();
////            assert 5 == repo.size();
////            System.out.println(repo.getAll().toString());
////        }
////        //assert 6 == repo.size();
////        //System.out.println(repo.getAll().toString());
////
////        Mockito.verify(validator, times(1)).validate(quiz);
////
////    }
////
////    @AfterEach
////    public void tearDown(){
////        repo = null;
////        quiz=null;
////    }
////
////}
//// */