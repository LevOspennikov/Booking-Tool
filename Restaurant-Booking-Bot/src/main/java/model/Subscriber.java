package model;

public class Subscriber {
    private String subscriberContact;
    private SubscriptionType type;

    public Subscriber(String subscriberContact) {
        this.subscriberContact = subscriberContact;
    }

    public Subscriber(String subscriberContact, String type) {
        this(subscriberContact);
        this.type = SubscriptionType.valueOf(type);
    }

    public String getSubscriptionType() {
        return type.toString();
    }

    public String getSubscriberContact() {
        return subscriberContact;
    }
}
