package junit.samples.money;
/**
 * A MoneyBag defers exchange rate conversions. For example adding
 * 12 Swiss Francs to 14 US Dollars is represented as a bag
 * containing the two Monies 12 CHF and 14 USD. Adding another
 * 10 Swiss francs gives a bag with 22 CHF and 14 USD. Due to
 * the deferred exchange rate conversion we can later value a
 * MoneyBag with different exchange rates.
 *
 * A MoneyBag is represented as a list of Monies and provides
 * different constructors to create a MoneyBag.
 */
class MoneyBag implements junit.samples.money.IMoney {
    private java.util.Vector fMonies = new java.util.Vector(5);

    private MoneyBag() {
    }

    MoneyBag(junit.samples.money.Money[] bag) {
        for (int i = 0; i < bag.length; i++) {
            if (!bag[i].isZero())
                appendMoney(bag[i]);

        }
    }

    MoneyBag(junit.samples.money.Money m1, junit.samples.money.Money m2) {
        appendMoney(m1);
        appendMoney(m2);
    }

    MoneyBag(junit.samples.money.Money m, junit.samples.money.MoneyBag bag) {
        appendMoney(m);
        appendBag(bag);
    }

    MoneyBag(junit.samples.money.MoneyBag m1, junit.samples.money.MoneyBag m2) {
        appendBag(m1);
        appendBag(m2);
    }

    public junit.samples.money.IMoney add(junit.samples.money.IMoney m) {
        return m.addMoneyBag(this);
    }

    public junit.samples.money.IMoney addMoney(junit.samples.money.Money m) {
        return new junit.samples.money.MoneyBag(m, this).simplify();
    }

    public junit.samples.money.IMoney addMoneyBag(junit.samples.money.MoneyBag s) {
        return new junit.samples.money.MoneyBag(s, this).simplify();
    }

    private void appendBag(junit.samples.money.MoneyBag aBag) {
        for (java.util.Enumeration e = aBag.fMonies.elements(); e.hasMoreElements();)
            appendMoney(((junit.samples.money.Money) (e.nextElement())));

    }

    private void appendMoney(junit.samples.money.Money aMoney) {
        junit.samples.money.IMoney old = findMoney(aMoney.currency());
        if (old == null) {
            fMonies.addElement(aMoney);
            return;
        }
        fMonies.removeElement(old);
        junit.samples.money.IMoney sum = old.add(aMoney);
        if (sum.isZero())
            return;

        fMonies.addElement(sum);
    }

    private boolean contains(junit.samples.money.Money aMoney) {
        junit.samples.money.Money m = findMoney(aMoney.currency());
        return m.amount() == aMoney.amount();
    }

    public boolean equals(java.lang.Object anObject) {
        if (isZero())
            if (anObject instanceof junit.samples.money.IMoney)
                return ((junit.samples.money.IMoney) (anObject)).isZero();


        if (anObject instanceof junit.samples.money.MoneyBag) {
            junit.samples.money.MoneyBag aMoneyBag = ((junit.samples.money.MoneyBag) (anObject));
            if (aMoneyBag.fMonies.size() != fMonies.size())
                return false;

            for (java.util.Enumeration e = fMonies.elements(); e.hasMoreElements();) {
                junit.samples.money.Money m = ((junit.samples.money.Money) (e.nextElement()));
                if (!aMoneyBag.contains(m))
                    return false;

            }
            return true;
        }
        return false;
    }

    private junit.samples.money.Money findMoney(java.lang.String currency) {
        for (java.util.Enumeration e = fMonies.elements(); e.hasMoreElements();) {
            junit.samples.money.Money m = ((junit.samples.money.Money) (e.nextElement()));
            if (m.currency().equals(currency))
                return m;

        }
        return null;
    }

    public int hashCode() {
        int hash = 0;
        for (java.util.Enumeration e = fMonies.elements(); e.hasMoreElements();) {
            java.lang.Object m = e.nextElement();
            hash ^= m.hashCode();
        }
        return hash;
    }

    public boolean isZero() {
        return fMonies.size() == 0;
    }

    public junit.samples.money.IMoney multiply(int factor) {
        junit.samples.money.MoneyBag result = new junit.samples.money.MoneyBag();
        if (factor != 0) {
            for (java.util.Enumeration e = fMonies.elements(); e.hasMoreElements();) {
                junit.samples.money.Money m = ((junit.samples.money.Money) (e.nextElement()));
                result.appendMoney(((junit.samples.money.Money) (m.multiply(factor))));
            }
        }
        return result;
    }

    public junit.samples.money.IMoney negate() {
        junit.samples.money.MoneyBag result = new junit.samples.money.MoneyBag();
        for (java.util.Enumeration e = fMonies.elements(); e.hasMoreElements();) {
            junit.samples.money.Money m = ((junit.samples.money.Money) (e.nextElement()));
            result.appendMoney(((junit.samples.money.Money) (m.negate())));
        }
        return result;
    }

    private junit.samples.money.IMoney simplify() {
        if (fMonies.size() == 1)
            return ((junit.samples.money.IMoney) (fMonies.elements().nextElement()));

        return this;
    }

    public junit.samples.money.IMoney subtract(junit.samples.money.IMoney m) {
        return add(m.negate());
    }

    public java.lang.String toString() {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        buffer.append("{");
        for (java.util.Enumeration e = fMonies.elements(); e.hasMoreElements();)
            buffer.append(((junit.samples.money.Money) (e.nextElement())));

        buffer.append("}");
        return buffer.toString();
    }
}