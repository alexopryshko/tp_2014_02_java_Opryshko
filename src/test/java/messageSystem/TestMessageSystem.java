package messageSystem;

import account.AccountService;
import frontend.Frontend;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TestMessageSystem {
    private class testMessage extends Message {
        private boolean wasExecuted = false;

        public testMessage(Address from, Address to) {
            super(from, to);
        }

        public void exec(Subscriber subscriber) {
            wasExecuted = true;
        }
    }

    private MessageSystem messageSystem = new MessageSystem();
    private Frontend frontend = mock(Frontend.class);
    private AccountService accountService = mock(AccountService.class);
    private Message message = mock(Message.class);

    @Before
    public void setUp(){
        Address frontendAddress = new Address();
        Address accountServiceAddress1 = new Address();
        when(frontend.getAddress()).thenReturn(frontendAddress);
        when(accountService.getAddress()).thenReturn(accountServiceAddress1);
        when(message.getTo()).thenReturn(frontendAddress);
    }

    @Test
    public void testAddService(){
        messageSystem.addService(frontend);
        boolean serviceAdded = messageSystem.getAddressService().getAddressCustomers(frontend.getCustomers()) == frontend.getAddress();
        Assert.assertTrue(serviceAdded);
    }

    @Test
    public void testBalanceBetweenAccountServices() {
        Address addressTestAccountService1 = new Address();
        Address addressTestAccountService2 = new Address();
        AccountService testAccountService1 = mock(AccountService.class);
        AccountService testAccountService2 = mock(AccountService.class);
        when(testAccountService1.getCustomers()).thenReturn(Customers.ACCOUNTSERVICE);
        when(testAccountService2.getCustomers()).thenReturn(Customers.ACCOUNTSERVICE);
        when(testAccountService1.getAddress()).thenReturn(addressTestAccountService1);
        when(testAccountService2.getAddress()).thenReturn(addressTestAccountService2);
        messageSystem.addService(testAccountService1);
        messageSystem.addService(testAccountService2);
        boolean result1 = false;
        boolean result2 = false;
        for (int i = 0; i < 5; i++) {
            result1 = testAccountService1.getAddress() == messageSystem.getAddressService().getAddressCustomers(Customers.ACCOUNTSERVICE);
            result2 = testAccountService2.getAddress() == messageSystem.getAddressService().getAddressCustomers(Customers.ACCOUNTSERVICE);
        }
        Assert.assertTrue(result1 && result2);
    }

    @Test
    public void testExecForSubscriberNullQueue(){
        messageSystem.addService(frontend);
        messageSystem.addService(accountService);
        testMessage newMessage = new testMessage(frontend.getAddress(), accountService.getAddress());
        messageSystem.sendMessage(newMessage);
        messageSystem.execForSubscriber(frontend);
        Assert.assertFalse(newMessage.wasExecuted);
    }

    @Test
    public void testExecForSubscriber(){
        messageSystem.addService(frontend);
        messageSystem.addService(accountService);
        testMessage newMessage = new testMessage(frontend.getAddress(), accountService.getAddress());
        messageSystem.sendMessage(newMessage);
        messageSystem.execForSubscriber(accountService);
        Assert.assertTrue(newMessage.wasExecuted);
    }
}
