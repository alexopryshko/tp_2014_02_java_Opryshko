package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {

    static private AtomicInteger subscriberIdCreator = new AtomicInteger();

    final private int subscriberID;

    public Address(){
        this.subscriberID = subscriberIdCreator.incrementAndGet();
    }

    @Override
    public int hashCode() {
        return subscriberID;
    }

    @Override
    public boolean equals(Object object) {
        if (getClass() != object.getClass())
            return false;
        Address address = (Address) object;
        return this.hashCode() == address.hashCode();
    }
}
