package junit.samples.money;
/**
 * The common interface for simple Monies and MoneyBags
 */
interface IMoney {
    /**
     * Adds a money to this money.
     */
    public abstract junit.samples.money.IMoney add(junit.samples.money.IMoney m);

    /**
     * Adds a simple Money to this money. This is a helper method for
     * implementing double dispatch
     */
    junit.samples.money.IMoney addMoney(junit.samples.money.Money m);

    /**
     * Adds a MoneyBag to this money. This is a helper method for
     * implementing double dispatch
     */
    junit.samples.money.IMoney addMoneyBag(junit.samples.money.MoneyBag s);

    /**
     * Tests whether this money is zero
     */
    public abstract boolean isZero();

    /**
     * Multiplies a money by the given factor.
     */
    public abstract junit.samples.money.IMoney multiply(int factor);

    /**
     * Negates this money.
     */
    public abstract junit.samples.money.IMoney negate();

    /**
     * Subtracts a money from this money.
     */
    public abstract junit.samples.money.IMoney subtract(junit.samples.money.IMoney m);
}