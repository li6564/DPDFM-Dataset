/**
 * Java Diagram Package; An extremely flexible and fast multipurpose diagram
 * component for Swing.
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package util;
/**
 *
 * @class WrappedIterator
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class WrappedIterator implements java.util.Iterator {
    private java.util.Iterator iter;

    private boolean readOnly;

    public WrappedIterator(java.util.Iterator iter) {
        this(iter, false);
    }

    public WrappedIterator(java.util.Iterator iter, boolean readOnly) {
        setIterator(iter);
        setReadOnly(readOnly);
    }

    public boolean hasNext() {
        return (iter != null) && iter.hasNext();
    }

    public java.lang.Object next() {
        if (iter != null)
            return iter.next();

        throw new java.util.NoSuchElementException();
    }

    public void remove() {
        if ((!readOnly) && (iter != null))
            iter.remove();

    }

    protected void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    protected boolean isReadOnly() {
        return readOnly;
    }

    protected java.util.Iterator getIterator() {
        return iter;
    }

    protected void setIterator(java.util.Iterator iter) {
        this.iter = iter;
    }
}