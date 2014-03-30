package messageSystem;

import account.AccountService;

public abstract class MessageToAccountService extends Message {

    public MessageToAccountService(Address from, Address to) {
        super(from, to);
    }

    void exec(Subscriber subscriber) {
        if(subscriber instanceof AccountService){
            exec((AccountService) subscriber);
        }
    }

    abstract void exec(AccountService accountService);
}
