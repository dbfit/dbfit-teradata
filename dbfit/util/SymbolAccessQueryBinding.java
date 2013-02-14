
/**
 * Copyright (c) Mark Matten (mark_matten@hotmail.com) 2011-13
 * Part of the dbfit-teradata software.
 * This software is subject to the provisions of
 * the GNU General Public License Version 2.0 (GPL).
 * See LICENCE.txt for details.
 */


package dbfit.util;

import fit.Binding;
import fit.Fixture;
import fit.Parse;

public class SymbolAccessQueryBinding extends Binding.QueryBinding {
	public void doCell(Fixture fixture, Parse cell) {
		String content=cell.text();
		System.out.println("SymbolAccessQueryBinding: doCell: cell.text(): "+content);
		try{
			if (content.startsWith(">>")){
				System.out.println("SymbolAccessQueryBinding: content.startsWith(>>)");
				Object value=this.adapter.get();
				dbfit.util.SymbolUtil.setSymbol(content.substring(2).trim(), value);
				cell.addToBody(Fixture.gray("= "+String.valueOf(value)));
//				fixture.ignore(cell);
				return;
			}
			if (content.startsWith("<<")){
				System.out.println("SymbolAccessQueryBinding: content.startsWith(<<");
				Object value=this.adapter.get();
				Object expected=this.adapter.parse(content);
				cell.addToBody(Fixture.gray("= "+String.valueOf(expected)));
				if (adapter.equals(value,expected))
					fixture.right(cell);
				else
					fixture.wrong(cell,String.valueOf(value));
				return;
			}
			if (content.startsWith("fail[")|| content.endsWith("]")){
				//expect failing comparison
				Object value=this.adapter.get();
				String expectedVal=content.substring(5, content.length()-1);
				cell.addToBody(Fixture.gray("= "+String.valueOf(value)));
				if (adapter.equals(value,adapter.parse(expectedVal)))
					fixture.wrong(cell);
				else
					fixture.right(cell);
				return;
			}
		}
		catch (Throwable t){
			fixture.exception(cell,t);
			return;
		}
		super.doCell(fixture,cell);
	}
}
