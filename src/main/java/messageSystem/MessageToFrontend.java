package messageSystem;
import frontend.Frontend;

public abstract class MessageToFrontend extends Message{
    public MessageToFrontend(Class from, Class to) {
        super(from, to);
    }

    public void exec(Subscriber subscriber) {
        if(subscriber instanceof Frontend){
            exec((Frontend)subscriber);
        }
    }

    abstract void exec(Frontend frontend);
}
