package model;

public class Subscriber {
    private String subscriberContact;
    private SubscribeType type;

    public Subscriber(String subscriberContact) {
        this.subscriberContact = subscriberContact;
    }

    public Subscriber(String subscriberContact, String type) {
        this(subscriberContact);
        this.type = SubscribeType.valueOf(type);
    }

    public String getSubscribeType() {
        return type.toString();
    }

    public String getSubscriberContact() {
        return subscriberContact;
    }
}
