package acme;
class IntHashtableEntry {
    int hash;

    int key;

    java.lang.Object value;

    acme.IntHashtableEntry next;

    protected java.lang.Object clone() {
        acme.IntHashtableEntry entry = new acme.IntHashtableEntry();
        entry.hash = hash;
        entry.key = key;
        entry.value = value;
        entry.next = (next != null) ? ((acme.IntHashtableEntry) (next.clone())) : null;
        return entry;
    }
}