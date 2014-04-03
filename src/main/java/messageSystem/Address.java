package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {

    static private AtomicInteger subscriberIdCreator = new AtomicInteger();

    final private int subscriberID;

    public Address(){
        this.subscriberID = subscriberIdCreator.incrementAndGet();
    }

    public int hashCode(){
        return subscriberID;
    }
}
