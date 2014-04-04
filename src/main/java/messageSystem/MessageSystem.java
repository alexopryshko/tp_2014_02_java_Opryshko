package messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {

    private Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private AddressService addressService = new AddressService();

    public void addService(Subscriber subscriber){
        messages.put(subscriber.getAddress(), new ConcurrentLinkedQueue<Message>());
        addressService.addCustomers(subscriber.getCustomers(), subscriber.getAddress());
    }

    public void sendMessage(Message message){
        Queue<Message> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForSubscriber(Subscriber subscriber) {
        Queue<Message> messageQueue = messages.get(subscriber.getAddress());
        if(messageQueue == null){
            return;
        }
        while(!messageQueue.isEmpty()){
            Message message = messageQueue.poll();
            message.exec(subscriber);
        }
    }

    public AddressService getAddressService(){
        return addressService;
    }
}
