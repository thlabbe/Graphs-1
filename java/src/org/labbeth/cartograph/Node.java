package org.labbeth.cartograph;

import java.util.List;

public class Node {
	String id;
	String type;
	String label;
	String debuginfo;
	
	public Node(String label, String type) {
		super();
		this.label = label;
		this.type = type;
		this.id = this.type + ":" + this.label; 
	}

	public void setDebuginfo(String debuginfo) {
		this.debuginfo = debuginfo;
	};
	public Node(String[] cols) {
		super();
		if (cols.length >= 2) {
			if (cols[2].contains(":")) {
				this.id = cols[2];
				String[] tmp = cols[2].split(":");
				this.label = tmp[1];
				this.type = tmp[0];
			} else {
				this.label = cols[2];
				this.id = this.label;
				this.type = cols[1];
			}

		} else {
			for (int i = 0; i < cols.length; i++) {
				System.err.println(" cols[" + i + "]:" + cols[i]);
			}
			this.label = "label-error";
			this.id = "id-error";
			this.type = "type-error";
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDebuginfo() {
		return debuginfo;
	}

	public String toJSONLinks(List<Link> links) {
		String jsonNode = this.toJSON();
		int pos = jsonNode.indexOf('}');
		jsonNode = jsonNode.substring(0, pos-1);
		StringBuilder sb = new StringBuilder();
		
		long targets = links.stream().filter(l -> l.target.equals(this.id)).count();
		long sources = links.stream().filter(l -> l.source.equals(this.id)).count();
		
		sb.append(jsonNode);
		sb.append(", \"sources\":");
		sb.append(sources);
		sb.append(", \"targets\":");
		sb.append(targets);
		sb.append(" }");
		
		return sb.toString();
	}
	
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":\"");
		sb.append(id);
		sb.append("\"");
		sb.append(", ");
		sb.append("\"label\":\"");
		sb.append(label);
		sb.append("\"");
		sb.append(", ");
		
		sb.append("\"type\":\"");
		sb.append(type);
		sb.append("\"");
		//sb.append(", ");
		//sb.append("\"group\":1");
		
		sb.append(" }");
		
		if ((this.debuginfo != null) && (!this.debuginfo.equals(""))) {
			sb.append("   /* ").append(this.debuginfo).append(" */");
		}
		
		return sb.toString();
	}

	public String toDot() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"").append(this.id).append("\"");
		sb.append("[");
		sb.append("label=\"").append(this.label).append("\"");
		sb.append("];");
		
		return sb.toString();
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
	
	
}
