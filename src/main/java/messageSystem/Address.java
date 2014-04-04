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
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        Address other = (Address) object;
        return subscriberID == other.subscriberID;
    }
}
