package junit.samples.money;
public class MoneyTest extends junit.framework.TestCase {
    private junit.samples.money.Money f12CHF;

    private junit.samples.money.Money f14CHF;

    private junit.samples.money.Money f7USD;

    private junit.samples.money.Money f21USD;

    private junit.samples.money.MoneyBag fMB1;

    private junit.samples.money.MoneyBag fMB2;

    public MoneyTest(java.lang.String name) {
        super(name);
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(junit.samples.money.MoneyTest.class);
        // junit.awtui.TestRunner.run(MoneyTest.class);
        // junit.swingui.TestRunner.run(MoneyTest.class);
    }

    protected void setUp() {
        f12CHF = new junit.samples.money.Money(12, "CHF");
        f14CHF = new junit.samples.money.Money(14, "CHF");
        f7USD = new junit.samples.money.Money(7, "USD");
        f21USD = new junit.samples.money.Money(21, "USD");
        fMB1 = new junit.samples.money.MoneyBag(f12CHF, f7USD);
        fMB2 = new junit.samples.money.MoneyBag(f14CHF, f21USD);
    }

    public void testBagMultiply() {
        // {[12 CHF][7 USD]} *2 == {[24 CHF][14 USD]}
        // Money bag[] = { new Money(24, "CHF"), new Money(14, "USD")};
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(-24, "CHF"), new junit.samples.money.Money(-14, "USD") };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, fMB1.multiply(2));
        junit.framework.Assert.assertEquals(fMB1, fMB1.multiply(1));
        junit.framework.Assert.assertTrue(fMB1.multiply(0).isZero());
    }

    public void testBagNegate() {
        // {[12 CHF][7 USD]} negate == {[-12 CHF][-7 USD]}
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(-12, "CHF"), new junit.samples.money.Money(-7, "USD") };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, fMB1.negate());
    }

    public void testBagSimpleAdd() {
        // {[12 CHF][7 USD]} + [14 CHF] == {[26 CHF][7 USD]}
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(26, "CHF"), new junit.samples.money.Money(7, "USD") };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, fMB1.add(f14CHF));
    }

    public void testBagSubtract() {
        // {[12 CHF][7 USD]} - {[14 CHF][21 USD] == {[-2 CHF][-14 USD]}
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(-2, "CHF"), new junit.samples.money.Money(-14, "USD") };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, fMB1.subtract(fMB2));
    }

    public void testBagSumAdd() {
        // {[12 CHF][7 USD]} + {[14 CHF][21 USD]} == {[26 CHF][28 USD]}
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(26, "CHF"), new junit.samples.money.Money(28, "USD") };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, fMB1.add(fMB2));
    }

    public void testIsZero() {
        junit.framework.Assert.assertTrue(fMB1.subtract(fMB1).isZero());
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(0, "CHF"), new junit.samples.money.Money(0, "USD") };
        junit.framework.Assert.assertTrue(new junit.samples.money.MoneyBag(bag).isZero());
    }

    public void testMixedSimpleAdd() {
        // [12 CHF] + [7 USD] == {[12 CHF][7 USD]}
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ f12CHF, f7USD };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, f12CHF.add(f7USD));
    }

    public void testMoneyBagEquals() {
        junit.framework.Assert.assertTrue(!fMB1.equals(null));
        junit.framework.Assert.assertEquals(fMB1, fMB1);
        junit.samples.money.MoneyBag equal = new junit.samples.money.MoneyBag(new junit.samples.money.Money(12, "CHF"), new junit.samples.money.Money(7, "USD"));
        junit.framework.Assert.assertTrue(fMB1.equals(equal));
        junit.framework.Assert.assertTrue(!fMB1.equals(f12CHF));
        junit.framework.Assert.assertTrue(!f12CHF.equals(fMB1));
        junit.framework.Assert.assertTrue(!fMB1.equals(fMB2));
    }

    public void testMoneyBagHash() {
        junit.samples.money.MoneyBag equal = new junit.samples.money.MoneyBag(new junit.samples.money.Money(12, "CHF"), new junit.samples.money.Money(7, "USD"));
        junit.framework.Assert.assertEquals(fMB1.hashCode(), equal.hashCode());
    }

    public void testMoneyEquals() {
        junit.framework.Assert.assertTrue(!f12CHF.equals(null));
        junit.samples.money.Money equalMoney = new junit.samples.money.Money(12, "CHF");
        junit.framework.Assert.assertEquals(f12CHF, f12CHF);
        junit.framework.Assert.assertEquals(f12CHF, equalMoney);
        junit.framework.Assert.assertEquals(f12CHF.hashCode(), equalMoney.hashCode());
        junit.framework.Assert.assertTrue(!f12CHF.equals(f14CHF));
    }

    public void testMoneyHash() {
        junit.framework.Assert.assertTrue(!f12CHF.equals(null));
        junit.samples.money.Money equal = new junit.samples.money.Money(12, "CHF");
        junit.framework.Assert.assertEquals(f12CHF.hashCode(), equal.hashCode());
    }

    public void testNormalize() {
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(26, "CHF"), new junit.samples.money.Money(28, "CHF"), new junit.samples.money.Money(6, "CHF") };
        junit.samples.money.MoneyBag moneyBag = new junit.samples.money.MoneyBag(bag);
        junit.samples.money.Money expected[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(60, "CHF") };
        // note: expected is still a MoneyBag
        junit.samples.money.MoneyBag expectedBag = new junit.samples.money.MoneyBag(expected);
        junit.framework.Assert.assertEquals(expectedBag, moneyBag);
    }

    public void testNormalize2() {
        // {[12 CHF][7 USD]} - [12 CHF] == [7 USD]
        junit.samples.money.Money expected = new junit.samples.money.Money(7, "USD");
        junit.framework.Assert.assertEquals(expected, fMB1.subtract(f12CHF));
    }

    public void testNormalize3() {
        // {[12 CHF][7 USD]} - {[12 CHF][3 USD]} == [4 USD]
        junit.samples.money.Money s1[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(12, "CHF"), new junit.samples.money.Money(3, "USD") };
        junit.samples.money.MoneyBag ms1 = new junit.samples.money.MoneyBag(s1);
        junit.samples.money.Money expected = new junit.samples.money.Money(4, "USD");
        junit.framework.Assert.assertEquals(expected, fMB1.subtract(ms1));
    }

    public void testNormalize4() {
        // [12 CHF] - {[12 CHF][3 USD]} == [-3 USD]
        junit.samples.money.Money s1[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(12, "CHF"), new junit.samples.money.Money(3, "USD") };
        junit.samples.money.MoneyBag ms1 = new junit.samples.money.MoneyBag(s1);
        junit.samples.money.Money expected = new junit.samples.money.Money(-3, "USD");
        junit.framework.Assert.assertEquals(expected, f12CHF.subtract(ms1));
    }

    public void testPrint() {
        junit.framework.Assert.assertEquals("[12 CHF]", f12CHF.toString());
    }

    public void testSimpleAdd() {
        // [12 CHF] + [14 CHF] == [26 CHF]
        junit.samples.money.Money expected = new junit.samples.money.Money(26, "CHF");
        junit.framework.Assert.assertEquals(expected, f12CHF.add(f14CHF));
    }

    public void testSimpleBagAdd() {
        // [14 CHF] + {[12 CHF][7 USD]} == {[26 CHF][7 USD]}
        junit.samples.money.Money bag[] = new junit.samples.money.Money[]{ new junit.samples.money.Money(26 / 0, "CHF"), new junit.samples.money.Money(7, "USD") };
        junit.samples.money.MoneyBag expected = new junit.samples.money.MoneyBag(bag);
        junit.framework.Assert.assertEquals(expected, f14CHF.add(fMB1));
    }

    public void testSimpleMultiply() {
        // [14 CHF] *2 == [28 CHF]
        junit.samples.money.Money expected = new junit.samples.money.Money(28, "CHF");
        junit.framework.Assert.assertEquals(expected, f14CHF.multiply(2));
    }

    public void testSimpleNegate() {
        // [14 CHF] negate == [-14 CHF]
        junit.samples.money.Money expected = new junit.samples.money.Money(-14, "CHF");
        junit.framework.Assert.assertEquals(expected, f14CHF.negate());
    }

    public void testSimpleSubtract() {
        // [14 CHF] - [12 CHF] == [2 CHF]
        junit.samples.money.Money expected = new junit.samples.money.Money(2, "CHF");
        junit.framework.Assert.assertEquals(expected, f14CHF.subtract(f12CHF));
    }
}