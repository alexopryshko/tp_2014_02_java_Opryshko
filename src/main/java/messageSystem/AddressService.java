package messageSystem;

import java.util.HashMap;
import java.util.Map;

public class AddressService {

    private Map<Customers, Address> addressService = new HashMap<>();

    public void addCustomers(Customers customers, Address address) {
        addressService.put(customers, address);
    }

    public Address getAddressCustomers(Customers customers) {
        return addressService.get(customers);
    }

}
