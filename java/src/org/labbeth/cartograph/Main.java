package org.labbeth.cartograph;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static  org.labbeth.cartograph.Config.*;

public class Main {

	
			
	public static void main(String[] args) {

		Config config = new ConfigTest();
		List<String> xrefs = config.getXrefFiles();

		List<Node> nodes = new ArrayList<Node>();
		List<Link> links = new ArrayList<Link>();
		
		String maintype = null;
		for (String xref : xrefs) {
			maintype = evalType(xref);
			
			try {
				List<String> lines = Files.readAllLines(Paths.get(PATH_TO_XREFS + "\\" + xref));
				for(String line : lines) {

					String[] cols = line.split(";");
					Node node = new Node(cols[0], maintype);
					if(DEBUG) {
						node.setDebuginfo(" xref = " + xref);
					}
					if (! nodes.contains(node)) {
						nodes.add(node);
					}
					
					Node node2 = new Node(cols);
					if (DEBUG) {
						node2.setDebuginfo(" xref = " + xref);
					}
					if (! nodes.contains(node2)) {
						nodes.add(node2);
					}
					
					Link link = new Link(node.id, node2.id);
					if (DEBUG) {
						link.setDebugInfo(" xref = " + xref);
					}
					if(! links.contains(link)) {
						links.add(link);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.err.println(xref + " done ");
			
		}
		/*
		String json = genJSON(nodes, links);
		System.out.println("");
		System.out.println(json);
		*/
		//String dot = genDot(nodes, links);
		
		//System.out.println(dot);
		String json = genJSON(nodes,links);

		System.out.println(json);
		try(FileWriter fw = new FileWriter(OUTPUT_PATH + OUTPUT_NODES_NAME)) {
			fw.write(onlyNodeToJSON(nodes, links));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try(FileWriter fw = new FileWriter(OUTPUT_PATH + OUTPUT_LINKS_NAME)) {
			fw.write(onlyLinkToJSON(nodes, links));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private static String genDot(List<Node> nodes, List<Link> links) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph {");
		sb.append(nodes.stream()
				 .map(Node::toDot)
				 .collect(Collectors.joining("\r\n")));
		
		sb.append(links.stream()
				 .map(l -> l.toDot(nodes)).collect(Collectors.joining("\r\n")));
				// .collect(Collectors.joining(",\r\n")));
		
		sb.append("}");
		
		
		return sb.toString();
	}

	private static String onlyNodeToJSON(List<Node> nodes, List<Link> links) {
		StringBuilder sb = new StringBuilder();
		sb.append("var nodeLIST = [ \r\n");
		sb.append(nodes.stream()
				 .map(n -> n.toJSONLinks(links))
				 .collect(Collectors.joining(",\r\n")));
		
		sb.append("];");
		return sb.toString();
	}
	
	private static String onlyLinkToJSON(List<Node> nodes, List<Link> links) {
		StringBuilder sb = new StringBuilder();
		sb.append("var linkLIST = [ \r\n");
		sb.append(links.stream()
				 .map(l -> l.toJSON())
				 .collect(Collectors.joining(",\r\n")));
		
		sb.append("];");
		return sb.toString();
	}
	
	private static String genJSON(List<Node> nodes, List<Link> links) {
		StringBuilder sb = new StringBuilder();
		sb.append("var json = { \r\n");
		
		sb.append("\"nodes\":");
		sb.append("[");
		
		sb.append(nodes.stream()
				 .map(Node::toJSON)
				 .collect(Collectors.joining(",\r\n")));
		
		
		sb.append("] \r\n");
		sb.append(", ");
		sb.append("\"links\":");
		sb.append("[");
		
		sb.append(links.stream()
				 .map(Link::toJSON)
				 .collect(Collectors.joining(",\r\n")));
		
		sb.append("] \r\n");
		sb.append("};");
		return sb.toString();
	}

	private static String evalType(String xref) {

//		fichier : XREF_BATEZT.txt
//		fichier : XREF_BATSRC.txt
//		fichier : XREF_BATSTW.txt
//		fichier : XREF_IMSDBD.txt
//		fichier : XREF_IMSPSB.txt
//		fichier : XREF_INOUT_JCLCRD.txt
//		fichier : XREF_JCLCRD.txt
//		fichier : XREF_ONLSRC.txt
//		fichier : XREF_TABSRC.txt
		if(xref.contains("BATEZT")) {
			return "BATEZT"; 
		} else if (xref.contains("BATSRC")){
			return "BATSRC";
		} else if (xref.contains("BATSTW")){
			return "BATSTW";
		} else if (xref.contains("IMSDBD")){
			return "IMSDBD";
		} else if (xref.contains("IMSPSB")){
			return "IMSPSB";
		} else if (xref.contains("JCLCRD")){
			return "JCLCRD";
		} else if (xref.contains("ONLSRC")){
			return "ONLSRC";
		} else if (xref.contains("TABSRC")){
			return "TABSRC";
		}
		return "?";
	}
}
