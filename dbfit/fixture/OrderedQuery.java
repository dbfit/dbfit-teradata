
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.fixture;

/**
 * Wrapper for the query fixture in standalone mode that initialises
 * ordered row comparisons
 */
public class OrderedQuery extends Query {
	@Override
	protected boolean isOrdered() {
		return true;
	}
}
