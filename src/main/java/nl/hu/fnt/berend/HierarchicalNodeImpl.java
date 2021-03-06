package nl.hu.fnt.berend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HierarchicalNodeImpl implements HierarchicalNode, Comparable<HierarchicalNodeImpl> {

	protected final Set<HierarchicalNode> nodes = new TreeSet<>();

	private final HierarchicalNode parent;

	private final String name;

	public HierarchicalNodeImpl(String name) {
		this(null, name);
	}

	public HierarchicalNodeImpl(HierarchicalNode parent, String aName) {
		super();
		this.parent = parent;
		if(aName == null){
			throw new IllegalArgumentException("Name is void");
		}
		this.name = aName;
		if(hasParent()){
			this.getParent().addChild(this);
		}
	}

	@Override
	public Iterator<HierarchicalNode> iterator() {
		return new HierarchicalNodeIterator(this);
	}

	@Override
	public HierarchicalNode getParent() {
		return this.parent;
	}

	public final boolean hasParent() {
		return parent != null;
	}
	
	@Override
	public boolean addChild(HierarchicalNode node) {
		if (node == null) {
			throw new IllegalArgumentException("HierarchicalNode node is null");
		}
		return this.nodes.add(node);
	}

	@Override
	public boolean removeChild(HierarchicalNode node) {
		if (node == null) {
			throw new IllegalArgumentException("HierarchicalNode node is null");
		}
		return this.nodes.remove(node);
	}

	@Override
	public Iterator<HierarchicalNode> children() {
		return this.nodes.iterator();
	}

	public final String getName() {
		return name;
	}

	@Override
	public String getPath() {
		StringBuilder builder = new StringBuilder();
		List<String> categoryPath = new ArrayList<>();
		HierarchicalNode node = this;
		while (node != null) {
			categoryPath.add(node.getName());
			node = node.getParent();
		}
		for (int i = categoryPath.size()-1; i > -1 ; i--) {
			builder.append('/');
			builder.append(categoryPath.get(i));
		}
		return builder.toString();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object that) {
		boolean isEqual = false;
		if (that != null) {
			isEqual = super.equals(that);
			if (!isEqual) {
				isEqual = this.equalByCompare(that);
			}
		}
		return isEqual;
	}
	/**
	 * Executes equals method using the compare to.
	 * 
	 * @param that
	 *            other object
	 * @return true if equal.
	 */
	private boolean equalByCompare(final Object that) {
		boolean isEqual;
		try {
			final HierarchicalNodeImpl thatObject =  (HierarchicalNodeImpl) that;
			isEqual = this.compareTo(thatObject) == 0;
		} catch (final ClassCastException e) {
			isEqual = false;
		} catch (final NullPointerException e) {
			isEqual = false;
		}
		return isEqual;
	}
	@Override
	public int compareTo(HierarchicalNodeImpl that) {
		return this.getName().compareTo(that.getName());
	}
	
	@Override
	public int hashCode() {
		return 13 ^ this.getName().hashCode();
	}
}
