package org.labbeth.cartograph;

import java.util.List;

public class Link {
	String source;
	String target;
	int value;
	String debugInfo;
	
	public Link(String source, String target) {
		this.source = source ;
		this.target = target;
		this.value = 1;
	}

	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"source\":\"");
		sb.append(this.source);
		sb.append("\"");
		
		sb.append(",");
		sb.append("\"target\":\"");
		sb.append(this.target);
		sb.append("\"");
		
		//sb.append(",");
		//sb.append("\"value\":");
		//sb.append(this.value);
		sb.append("}");
		
		if ((this.debugInfo != null) && (!this.debugInfo.equals(""))) {
			sb.append("   /* ").append(this.debugInfo).append(" */");
		}
		
		return sb.toString();
		
	}
	
	public String toDot(List<Node> nodes) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(source);
		sb.append("\" -> \"");
		sb.append(target);
		sb.append("\";");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + value;
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
		Link other = (Link) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	
}
