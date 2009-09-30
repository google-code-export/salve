package salve.expr.util;

import java.util.Iterator;

import salve.Bytecode;
import salve.BytecodeLoader;

public class ClassHieararchyIterator implements Iterator<EnhancedClassReader> {
	private final BytecodeLoader loader;
	private String className;

	public ClassHieararchyIterator(BytecodeLoader loader, String className) {
		this.loader = loader;
		this.className = className;
	}

	public boolean hasNext() {
		return !"java/lang/Object".equals(className);
	}

	public EnhancedClassReader next() {
		Bytecode bytecode= loader.loadBytecode(className);
		if (bytecode == null) {
			throw new IllegalStateException("Could not load bytecode for class: " + className);
		}
		EnhancedClassReader reader = new EnhancedClassReader(bytecode.getBytes());
		className = reader.getSuperName();
		return reader;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}