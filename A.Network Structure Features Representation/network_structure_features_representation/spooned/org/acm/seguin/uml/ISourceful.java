/* Author:  Eric Hodges

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * An interface for all "EndPointPanels" that have Java source code backing
 *  them.
 *
 * @author Eric Hodges
 */
public interface ISourceful {
    /**
     * Get the Summary for this object.
     *
     * @return the summary
     */
    public org.acm.seguin.summary.Summary getSourceSummary();
}