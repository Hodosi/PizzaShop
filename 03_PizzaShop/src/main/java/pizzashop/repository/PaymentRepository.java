package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private static String filename = "data/payments.txt";
    private List<Payment> paymentList;

    public PaymentRepository(){
        this.paymentList = new ArrayList<>();
        readPayments();
    }

    public PaymentRepository(final String newFilename){
        this.paymentList = new ArrayList<>();
        filename = newFilename;
    }

    private void readPayments(){
        //ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(filename);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Payment payment=getPayment(line);
                paymentList.add(payment);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payment getPayment(String line){
        Payment item=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        int tableNumber= Integer.parseInt(st.nextToken());
        String type= st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment) throws Exception {
        validatePayment(payment);
        paymentList.add(payment);
        writeAll();
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){
        //ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(filename);

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Payment p:paymentList) {
                System.out.println(p.toString());
                bw.write(p.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validatePayment(final Payment payment) throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();

        final int masa = payment.getTableNumber();
        if(masa < 1 || masa > 8){
            stringBuilder.append("invalid table! ");
        }

        final double valaore = payment.getAmount();
        if(valaore <= 0){
            stringBuilder.append("invalid value! ");
        }

        final String exception = stringBuilder.toString();
        if(exception.length() > 0){
            throw new Exception(exception);
        }
    }
}
