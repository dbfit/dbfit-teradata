
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import fit.Fixture;
import fit.FixtureLoader;
import fit.Parse;

/**
 * Make it possible to remove a previously imported fixture from Fit's fixture
 * path.
 */
public class ExportFixture extends Fixture {
	public void doRow(Parse row) {
		String packageName = row.parts.text();
		FixtureLoader.instance().fixturePathElements.remove(packageName);
	}
}
