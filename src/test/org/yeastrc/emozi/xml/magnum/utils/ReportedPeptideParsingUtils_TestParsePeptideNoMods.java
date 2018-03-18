package org.yeastrc.emozi.xml.magnum.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yeastrc.emozi.xml.magnum.objects.ParsedPeptide;

public class ReportedPeptideParsingUtils_TestParsePeptideNoMods {

	@Test
	public void test_noMods() {

		ParsedPeptide parsedPeptide = ReportedPeptideParsingUtils.parsePeptide( "PEPTIDE" );
		
		assertEquals( "PEPTIDE", parsedPeptide.getNakedSequence() );
		assertEquals( 0, parsedPeptide.getModMap().size() );
		
	}
	
}
