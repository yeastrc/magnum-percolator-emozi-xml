package org.yeastrc.emozi.xml.magnum.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yeastrc.emozi.xml.magnum.objects.MagnumParameters;

public class MagnumParamsReader {

	/**
	 * Get the relevant parameters from the magnum params file
	 * 
	 * @param paramsFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static MagnumParameters getMagnumParameters( File paramsFile ) throws FileNotFoundException, IOException {
		
		MagnumParameters magParams = new MagnumParameters();
		
		try ( InputStream is = new FileInputStream( paramsFile ) ) {
			magParams.setDynamicMods( getDynamicModsFromParamsFile( is ) );
		}
		
		try ( InputStream is = new FileInputStream( paramsFile ) ) {
			magParams.setStaticMods( getStaticModsFromParamsFile( is ) );
		}
		
		
		return magParams;
	}
	
	
	/**
	 * 
	 * Example line: modification = C   57.02146 #foo comments
	 * 
	 * @param paramsInputStream
	 * @return
	 * @throws IOException
	 */
	public static Map<Character, Double> getDynamicModsFromParamsFile( InputStream paramsInputStream ) throws IOException {
		
		Map<Character, Double> staticMods = new HashMap<>();
		
		Pattern p = Pattern.compile( "^modification\\s+=\\s+([A-Z])\\s+([0-9]+(\\.[0-9]+)?).*$" );
		
	    try (BufferedReader br = new BufferedReader( new InputStreamReader( paramsInputStream ) ) ) {
	    	
			for ( String line = br.readLine(); line != null; line = br.readLine() ) {		

				// skip immediately if it's not a line we want
				if( !line.startsWith( "modification" ) )
						continue;
				
				Matcher m = p.matcher( line );
				if( m.matches() ) {
					char residue = m.group( 1 ).charAt( 0 );					
					double d = Double.valueOf( m.group( 2 ) );
					
					if( d >= 0.000001 )
						staticMods.put( residue, d );
				}
			}
	    	
	    }
		
		return staticMods;
	}
	
	
	/**
	 * 
	 * Example line: modification = C   57.02146 #foo comments
	 * 
	 * @param paramsInputStream
	 * @return
	 * @throws IOException
	 */
	public static Map<Character, Double> getStaticModsFromParamsFile( InputStream paramsInputStream ) throws IOException {
		
		Map<Character, Double> staticMods = new HashMap<>();
		
		Pattern p = Pattern.compile( "^fixed_modification\\s+=\\s+([A-Z])\\s+([0-9]+(\\.[0-9]+)?).*$" );
		
	    try (BufferedReader br = new BufferedReader( new InputStreamReader( paramsInputStream ) ) ) {
	    	
			for ( String line = br.readLine(); line != null; line = br.readLine() ) {		

				// skip immediately if it's not a line we want
				if( !line.startsWith( "fixed_modification" ) )
						continue;
				
				Matcher m = p.matcher( line );
				if( m.matches() ) {
					char residue = m.group( 1 ).charAt( 0 );					
					double d = Double.valueOf( m.group( 2 ) );
					
					if( d >= 0.000001 )
						staticMods.put( residue, d );
				}
			}
	    	
	    }
		
		return staticMods;
	}
	
}
