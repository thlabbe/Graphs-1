package org.labbeth.cartograph;

import java.util.List;

public interface Config {
	public final static String PATH_TO_XREFS = "D:\\Projets\\DeGoudse\\CartoWP5\\A.Analyse";
	public final static boolean DEBUG = false;
	public final static String OUTPUT_PATH = "E:\\Users\\Thomas LABBE\\test-node\\graphes\\data\\";
	public final static String OUTPUT_JSON_NAME = "fullgraph.json";
	public final static String OUTPUT_NODES_NAME = "nodeList.json";
	public final static String OUTPUT_LINKS_NAME = "linkList.json";
	List<String> getXrefFiles();

	
}
