package salve.contract.pe;

import java.util.Map;

public interface Policy {
	Accessor choose(Map<Accessor.Type, Accessor> accessors);
}