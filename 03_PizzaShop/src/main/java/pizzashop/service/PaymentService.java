package pizzashop.service;

import pizzashop.model.MenuData;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PaymentService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public PaymentService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuData> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, double amount) throws Exception {
        Payment payment= new Payment(table, type, amount);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type) {
        double total = 0.0d;
        List<Payment> l = getPayments();
        if (l != null) {
            if (!l.isEmpty()) {
                int i = 0;
                while (i < l.size()) {
                    Payment p = l.get(i);
                    if (p.getType().equals(type)) {
                        total += p.getAmount();
                    }
                    i++;
                }
            }
        }
        return total;
    }
}
