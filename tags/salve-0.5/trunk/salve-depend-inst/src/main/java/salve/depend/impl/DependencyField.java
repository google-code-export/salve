/**
 * 
 */
package salve.depend.impl;

import salve.asmlib.Type;

class DependencyField {
	String name;
	String desc;
	String owner;
	String strategy = Constants.STRAT_REMOVE;

	public DependencyField(String owner, String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
		this.owner = owner;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DependencyField other = (DependencyField) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		return true;
	}

	public String getDesc() {
		return desc;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public String getStrategy() {
		return strategy;
	}

	public Type getType() {
		return Type.getType(desc);
	}

	@Override public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (owner == null ? 0 : owner.hashCode());
		return result;
	}

	public void setStrategy(String strat) {
		if (!Constants.STRAT_REMOVE.equals(strat) && !Constants.STRAT_INJECT.equals(strat)) {
			throw new IllegalStateException("Invalid strategy value: " + strat);
		}
		this.strategy = strat;
	}

}