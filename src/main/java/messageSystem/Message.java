package messageSystem;


public abstract class Message {
    private Class from;
    private Class to;

    public Message(Class from, Class to){
        this.from = from;
        this.to = to;
    }

    protected Class getFrom(){
        return from;
    }

    protected Class getTo(){
        return to;
    }

    abstract void exec(Subscriber subscriber);
}
