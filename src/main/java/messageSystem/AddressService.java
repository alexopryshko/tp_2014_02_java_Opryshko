package messageSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AddressService {

    private Map<Customers, ConcurrentLinkedQueue<Address>> addressService = new HashMap<>();

    public void addCustomers(Customers customers, Address address) {
        if (addressService.containsKey(customers)) {
            addressService.get(customers).add(address);
            return;
        }
        ConcurrentLinkedQueue<Address> list = new ConcurrentLinkedQueue<>();
        list.add(address);
        addressService.put(customers, list);
    }

    public Address getAddressCustomers(Customers customers) {
        ConcurrentLinkedQueue<Address> list = addressService.get(customers);
        Address address = list.poll();
        list.add(address);
        return address;
    }

}
