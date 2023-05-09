package pizzashop.repository;

import pizzashop.model.Payment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentValidator {
    public String validatePayment(final Payment payment) {
        final int masa = payment.getTableNumber();
        if (masa < 1 || masa > 8) {
            return "invalid table! ";
        }

        final double valaore = payment.getAmount();
        if (valaore <= 0) {
            return "invalid value! ";
        }

        return "";
    }

    public String validatePaymentList(List<Payment> paymentList) {
        Set<Payment> paymentSet = new HashSet<>(paymentList);
        if (paymentSet.size() != paymentList.size()) {
            return "corrupted list";
        }
        return "";
    }
}
