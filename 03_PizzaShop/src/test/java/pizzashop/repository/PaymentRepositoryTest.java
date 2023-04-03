package pizzashop.repository;

import org.junit.jupiter.api.*;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    final String filename = "data/testPayments.txt";

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository(filename);
    }

    @Test
    @DisplayName("TC1_ECP")
    @Tag("ECP")
    @Order(1)
    void add_TC1_ECP() {
        int masa = 2;
        final PaymentType tip = PaymentType.Cash;
        double valoare = -7;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            assert false;
        } catch (Exception exception) {
            assertEquals("invalid value! ", exception.getMessage());
        }
    }

    @Test
    @DisplayName("TC2_ECP")
    @Tag("ECP")
    @Order(2)
//    @Disabled
    void add_TC2_ECP() {
        int masa = 3;
        final PaymentType tip = PaymentType.Card;
        double valoare = 35.5;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            Payment readPayment = readPayment();
            assert readPayment != null;
            assertEquals(readPayment.getTableNumber(), payment.getTableNumber());
            assertEquals(readPayment.getType(), payment.getType());
            assertEquals(readPayment.getAmount(), payment.getAmount());
        } catch (Exception exception) {
            assert false;
        }
    }

    @Test
    @DisplayName("TC3_ECP")
    @Tag("ECP")
    @Order(3)
    void add_TC3_ECP() {
        int masa = -4;
        final PaymentType tip = PaymentType.Cash;
        double valoare = 38;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            assert false;
        } catch (Exception exception) {
            assertEquals("invalid table! ", exception.getMessage());
        }
    }

    @Test
    @DisplayName("TC1_BVA")
    @Tag("BVA")
    @Order(4)
    void add_TC1_BVA() {
        int masa = 0;
        PaymentType tip = PaymentType.Cash;
        double valoare = 1;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            assert false;
        } catch (Exception exception) {
            assertEquals("invalid table! ", exception.getMessage());
        }
    }

    @Test
    @DisplayName("TC2_BVA")
    @Tag("BVA")
    @Order(5)
    void add_TC2_BVA() {
        int masa = 9;
        PaymentType tip = PaymentType.Card;
        double valoare = 0;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            assert false;
        } catch (Exception exception) {
            assertEquals("invalid table! invalid value! ", exception.getMessage());
        }
    }

    @Test
    @DisplayName("TC3_BVA")
    @Tag("BVA")
    @Order(6)
//    @Disabled
    void add_TC3_BVA() {
        int masa = 1;
        PaymentType tip = PaymentType.Cash;
        double valoare = 1;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            Payment readPayment = readPayment();
            assert readPayment != null;
            assertEquals(readPayment.getTableNumber(), payment.getTableNumber());
            assertEquals(readPayment.getType(), payment.getType());
            assertEquals(readPayment.getAmount(), payment.getAmount());
        } catch (Exception exception) {
            assert false;
        }
    }

    @Test
    @DisplayName("TC4_BVA")
    @Tag("BVA")
    @Order(7)
//    @Disabled
    void add_TC4_BVA() {
        int masa = 8;
        PaymentType tip = PaymentType.Card;
        double valoare = 1;

        final Payment payment = new Payment(masa, tip, valoare);
        try {
            paymentRepository.add(payment);
            Payment readPayment = readPayment();
            assert readPayment != null;
            assertEquals(readPayment.getTableNumber(), payment.getTableNumber());
            assertEquals(readPayment.getType(), payment.getType());
            assertEquals(readPayment.getAmount(), payment.getAmount());
        } catch (Exception exception) {
            assert false;
        }
    }

    private Payment readPayment() {
        File file = new File(filename);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            if ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                int tableNumber = Integer.parseInt(st.nextToken());
                String type = st.nextToken();
                double amount = Double.parseDouble(st.nextToken());

                return new Payment(tableNumber, PaymentType.valueOf(type), amount);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}