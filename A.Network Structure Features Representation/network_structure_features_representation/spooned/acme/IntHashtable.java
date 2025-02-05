package acme;
// / A Hashtable that uses ints as the keys.
// <P>
// Use just like java.util.Hashtable, except that the keys must be ints.
// This is much faster than creating a new Integer for each access.
// <P>
// <A HREF="/resources/classes/Acme/IntHashtable.java">Fetch the software.</A><BR>
// <A HREF="/resources/classes/Acme.tar.gz">Fetch the entire Acme package.</A>
// <P>
// @see java.util.Hashtable
public class IntHashtable extends java.util.Dictionary implements java.lang.Cloneable {
    // / The hash table data.
    private acme.IntHashtableEntry table[];

    // / The total number of entries in the hash table.
    private int count;

    // / Rehashes the table when count exceeds this threshold.
    private int threshold;

    // / The load factor for the hashtable.
    private float loadFactor;

    // / Constructs a new, empty hashtable with the specified initial
    // capacity and the specified load factor.
    // @param initialCapacity the initial number of buckets
    // @param loadFactor a number between 0.0 and 1.0, it defines
    // the threshold for rehashing the hashtable into
    // a bigger one.
    // @exception IllegalArgumentException If the initial capacity
    // is less than or equal to zero.
    // @exception IllegalArgumentException If the load factor is
    // less than or equal to zero.
    public IntHashtable(int initialCapacity, float loadFactor) {
        if ((initialCapacity <= 0) || (loadFactor <= 0.0))
            throw new java.lang.IllegalArgumentException();

        this.loadFactor = loadFactor;
        table = new acme.IntHashtableEntry[initialCapacity];
        threshold = ((int) (initialCapacity * loadFactor));
    }

    // / Constructs a new, empty hashtable with the specified initial
    // capacity.
    // @param initialCapacity the initial number of buckets
    public IntHashtable(int initialCapacity) {
        this(initialCapacity, 0.75F);
    }

    // / Constructs a new, empty hashtable. A default capacity and load factor
    // is used. Note that the hashtable will automatically grow when it gets
    // full.
    public IntHashtable() {
        this(101, 0.75F);
    }

    // / Returns the number of elements contained in the hashtable.
    public int size() {
        return count;
    }

    // / Returns true if the hashtable contains no elements.
    public boolean isEmpty() {
        return count == 0;
    }

    // / Returns an enumeration of the hashtable's keys.
    // @see IntHashtable#elements
    public synchronized java.util.Enumeration keys() {
        return new acme.IntHashtableEnumerator(table, true);
    }

    // / Returns an enumeration of the elements. Use the Enumeration methods
    // on the returned object to fetch the elements sequentially.
    // @see IntHashtable#keys
    public synchronized java.util.Enumeration elements() {
        return new acme.IntHashtableEnumerator(table, false);
    }

    // / Returns true if the specified object is an element of the hashtable.
    // This operation is more expensive than the containsKey() method.
    // @param value the value that we are looking for
    // @exception NullPointerException If the value being searched
    // for is equal to null.
    // @see IntHashtable#containsKey
    public synchronized boolean contains(java.lang.Object value) {
        if (value == null)
            throw new java.lang.NullPointerException();

        acme.IntHashtableEntry tab[] = table;
        for (int i = tab.length; (i--) > 0;) {
            for (acme.IntHashtableEntry e = tab[i]; e != null; e = e.next) {
                if (e.value.equals(value))
                    return true;

            }
        }
        return false;
    }

    // / Returns true if the collection contains an element for the key.
    // @param key the key that we are looking for
    // @see IntHashtable#contains
    public synchronized boolean containsKey(int key) {
        acme.IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for (acme.IntHashtableEntry e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && (e.key == key))
                return true;

        }
        return false;
    }

    // / Gets the object associated with the specified key in the
    // hashtable.
    // @param key the specified key
    // @returns the element for the key or null if the key
    // is not defined in the hash table.
    // @see IntHashtable#put
    public synchronized java.lang.Object get(int key) {
        acme.IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for (acme.IntHashtableEntry e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && (e.key == key))
                return e.value;

        }
        return null;
    }

    // / A get method that takes an Object, for compatibility with
    // java.util.Dictionary.  The Object must be an Integer.
    public java.lang.Object get(java.lang.Object okey) {
        if (!(okey instanceof java.lang.Integer))
            throw new java.lang.InternalError("key is not an Integer");

        java.lang.Integer ikey = ((java.lang.Integer) (okey));
        int key = ikey.intValue();
        return get(key);
    }

    // / Rehashes the content of the table into a bigger table.
    // This method is called automatically when the hashtable's
    // size exceeds the threshold.
    protected void rehash() {
        int oldCapacity = table.length;
        acme.IntHashtableEntry oldTable[] = table;
        int newCapacity = (oldCapacity * 2) + 1;
        acme.IntHashtableEntry newTable[] = new acme.IntHashtableEntry[newCapacity];
        threshold = ((int) (newCapacity * loadFactor));
        table = newTable;
        for (int i = oldCapacity; (i--) > 0;) {
            for (acme.IntHashtableEntry old = oldTable[i]; old != null;) {
                acme.IntHashtableEntry e = old;
                old = old.next;
                int index = (e.hash & 0x7fffffff) % newCapacity;
                e.next = newTable[index];
                newTable[index] = e;
            }
        }
    }

    // / Puts the specified element into the hashtable, using the specified
    // key.  The element may be retrieved by doing a get() with the same key.
    // The key and the element cannot be null.
    // @param key the specified key in the hashtable
    // @param value the specified element
    // @exception NullPointerException If the value of the element
    // is equal to null.
    // @see IntHashtable#get
    // @return the old value of the key, or null if it did not have one.
    public synchronized java.lang.Object put(int key, java.lang.Object value) {
        // Make sure the value is not null.
        if (value == null)
            throw new java.lang.NullPointerException();

        // Makes sure the key is not already in the hashtable.
        acme.IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for (acme.IntHashtableEntry e = tab[index]; e != null; e = e.next) {
            if ((e.hash == hash) && (e.key == key)) {
                java.lang.Object old = e.value;
                e.value = value;
                return old;
            }
        }
        if (count >= threshold) {
            // Rehash the table if the threshold is exceeded.
            rehash();
            return put(key, value);
        }
        // Creates the new entry.
        acme.IntHashtableEntry e = new acme.IntHashtableEntry();
        e.hash = hash;
        e.key = key;
        e.value = value;
        e.next = tab[index];
        tab[index] = e;
        ++count;
        return null;
    }

    // / A put method that takes an Object, for compatibility with
    // java.util.Dictionary.  The Object must be an Integer.
    public java.lang.Object put(java.lang.Object okey, java.lang.Object value) {
        if (!(okey instanceof java.lang.Integer))
            throw new java.lang.InternalError("key is not an Integer");

        java.lang.Integer ikey = ((java.lang.Integer) (okey));
        int key = ikey.intValue();
        return put(key, value);
    }

    // / Removes the element corresponding to the key. Does nothing if the
    // key is not present.
    // @param key the key that needs to be removed
    // @return the value of key, or null if the key was not found.
    public synchronized java.lang.Object remove(int key) {
        acme.IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7fffffff) % tab.length;
        for (acme.IntHashtableEntry e = tab[index], prev = null; e != null; prev = e , e = e.next) {
            if ((e.hash == hash) && (e.key == key)) {
                if (prev != null)
                    prev.next = e.next;
                else
                    tab[index] = e.next;

                --count;
                return e.value;
            }
        }
        return null;
    }

    // / A remove method that takes an Object, for compatibility with
    // java.util.Dictionary.  The Object must be an Integer.
    public java.lang.Object remove(java.lang.Object okey) {
        if (!(okey instanceof java.lang.Integer))
            throw new java.lang.InternalError("key is not an Integer");

        java.lang.Integer ikey = ((java.lang.Integer) (okey));
        int key = ikey.intValue();
        return remove(key);
    }

    // / Clears the hash table so that it has no more elements in it.
    public synchronized void clear() {
        acme.IntHashtableEntry tab[] = table;
        for (int index = tab.length; (--index) >= 0;)
            tab[index] = null;

        count = 0;
    }

    // / Creates a clone of the hashtable. A shallow copy is made,
    // the keys and elements themselves are NOT cloned. This is a
    // relatively expensive operation.
    public synchronized java.lang.Object clone() {
        try {
            acme.IntHashtable t = ((acme.IntHashtable) (super.clone()));
            t.table = new acme.IntHashtableEntry[table.length];
            for (int i = table.length; (i--) > 0;)
                t.table[i] = (table[i] != null) ? ((acme.IntHashtableEntry) (table[i].clone())) : null;

            return t;
        } catch (java.lang.CloneNotSupportedException e) {
            // This shouldn't happen, since we are Cloneable.
            throw new java.lang.InternalError();
        }
    }

    // / Converts to a rather lengthy String.
    public synchronized java.lang.String toString() {
        int max = size() - 1;
        java.lang.StringBuffer buf = new java.lang.StringBuffer();
        java.util.Enumeration k = keys();
        java.util.Enumeration e = elements();
        buf.append("{");
        for (int i = 0; i <= max; ++i) {
            java.lang.String s1 = k.nextElement().toString();
            java.lang.String s2 = e.nextElement().toString();
            buf.append((s1 + "=") + s2);
            if (i < max)
                buf.append(", ");

        }
        buf.append("}");
        return buf.toString();
    }
}