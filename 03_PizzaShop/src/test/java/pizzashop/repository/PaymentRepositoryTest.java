package pizzashop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    final String filename = "data/testPayments.txt";

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository(filename);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add_TC1_ECP() {
        int masa = 2;
        final PaymentType tip = PaymentType.Cash;
        double valoare = -7;

        final Payment payment = new Payment(masa, tip, valoare);
        try{
            paymentRepository.add(payment);
        } catch (Exception exception){
            assertEquals("invalid value! ", exception.getMessage());
        }
    }

    @Test
    void add_TC2_ECP() {
        int masa = 3;
        final PaymentType tip = PaymentType.Card;
        double valoare = 35.5;

        final Payment payment = new Payment(masa, tip, valoare);
        try{
            paymentRepository.add(payment);
            // todo: plata inregistrata

            assert true;
        } catch (Exception exception){
            assert false;
        }
    }

    @Test
    void add_TC3_ECP() {
        int masa = -4;
        final PaymentType tip = PaymentType.Cash;
        double valoare = 38;

        final Payment payment = new Payment(masa, tip, valoare);
        try{
            paymentRepository.add(payment);
        } catch (Exception exception){
            assertEquals("invalid value! ", exception.getMessage());
        }
    }





    private Payment readPayment(){
        File file = new File(filename);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            if((line=br.readLine())!=null){
                StringTokenizer st=new StringTokenizer(line, ",");
                int tableNumber= Integer.parseInt(st.nextToken());
                String type= st.nextToken();
                double amount = Double.parseDouble(st.nextToken());
                final Payment payment = new Payment(tableNumber, PaymentType.valueOf(type), amount);

                return payment;
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