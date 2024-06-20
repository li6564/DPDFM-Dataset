package junit.samples.money;
/**
 * A simple Money.
 */
class Money implements junit.samples.money.IMoney {
    private int fAmount;

    private java.lang.String fCurrency;

    /**
     * Constructs a money from the given amount and currency.
     */
    public Money(int amount, java.lang.String currency) {
        fAmount = amount;
        fCurrency = currency;
    }

    /**
     * Adds a money to this money. Forwards the request to the addMoney helper.
     */
    public junit.samples.money.IMoney add(junit.samples.money.IMoney m) {
        return m.addMoney(this);
    }

    public junit.samples.money.IMoney addMoney(junit.samples.money.Money m) {
        if (m.currency().equals(currency()))
            return new junit.samples.money.Money(amount() + m.amount(), currency());

        return new junit.samples.money.MoneyBag(this, m);
    }

    public junit.samples.money.IMoney addMoneyBag(junit.samples.money.MoneyBag s) {
        return s.addMoney(this);
    }

    public int amount() {
        return fAmount;
    }

    public java.lang.String currency() {
        return fCurrency;
    }

    public boolean equals(java.lang.Object anObject) {
        if (isZero())
            if (anObject instanceof junit.samples.money.IMoney)
                return ((junit.samples.money.IMoney) (anObject)).isZero();


        if (anObject instanceof junit.samples.money.Money) {
            junit.samples.money.Money aMoney = ((junit.samples.money.Money) (anObject));
            return aMoney.currency().equals(currency()) && (amount() == aMoney.amount());
        }
        return false;
    }

    public int hashCode() {
        return fCurrency.hashCode() + fAmount;
    }

    public boolean isZero() {
        return amount() == 0;
    }

    public junit.samples.money.IMoney multiply(int factor) {
        return new junit.samples.money.Money(amount() * factor, currency());
    }

    public junit.samples.money.IMoney negate() {
        return new junit.samples.money.Money(-amount(), currency());
    }

    public junit.samples.money.IMoney subtract(junit.samples.money.IMoney m) {
        return add(m.negate());
    }

    public java.lang.String toString() {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        buffer.append(((("[" + amount()) + " ") + currency()) + "]");
        return buffer.toString();
    }
}