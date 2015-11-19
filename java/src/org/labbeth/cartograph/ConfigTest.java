package org.labbeth.cartograph;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigTest implements Config {
	

	
	public ConfigTest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getXrefFiles() {
		Path path = Paths.get(Config.PATH_TO_XREFS);
		FilenameFilter fileter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				
				return name.startsWith("XREF_") && (!name.contains("INOUT"));
			}
		};

		File dir = new File(PATH_TO_XREFS);
		
		List<String> res = new ArrayList<>();
		String[] xxx = dir.list(fileter);
		for (int i = 0; i < xxx.length; i++) {
			res.add(xxx[i]);
		}
		return res;
	}

}
