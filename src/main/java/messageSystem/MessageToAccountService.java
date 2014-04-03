package messageSystem;

import account.AccountService;

public abstract class MessageToAccountService extends Message {

    public MessageToAccountService(Class from, Class to) {
        super(from, to);
    }

    void exec(Subscriber subscriber) {
        if(subscriber instanceof AccountService){
            exec((AccountService) subscriber);
        }
    }

    abstract void exec(AccountService accountService);
}
