package acme;
class IntHashtableEnumerator implements java.util.Enumeration {
    boolean keys;

    int index;

    acme.IntHashtableEntry table[];

    acme.IntHashtableEntry entry;

    IntHashtableEnumerator(acme.IntHashtableEntry[] table, boolean keys) {
        this.table = table;
        this.keys = keys;
        this.index = table.length;
    }

    public boolean hasMoreElements() {
        if (entry != null)
            return true;

        while ((index--) > 0)
            if ((entry = table[index]) != null)
                return true;


        return false;
    }

    public java.lang.Object nextElement() {
        if (entry == null)
            while (((index--) > 0) && ((entry = table[index]) == null));

        if (entry != null) {
            acme.IntHashtableEntry e = entry;
            entry = e.next;
            return keys ? new java.lang.Integer(e.key) : e.value;
        }
        throw new java.util.NoSuchElementException("IntHashtableEnumerator");
    }
}