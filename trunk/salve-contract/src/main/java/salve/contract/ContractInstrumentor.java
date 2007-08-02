package salve.contract;

import salve.BytecodeLoader;
import salve.CannotLoadBytecodeException;
import salve.InstrumentationException;
import salve.Instrumentor;
import salve.contract.impl.NotEmptyInstrumentor;
import salve.contract.impl.NotNullInstrumentor;
import salve.org.objectweb.asm.ClassReader;
import salve.org.objectweb.asm.ClassWriter;

public class ContractInstrumentor implements Instrumentor {

	public byte[] instrument(String className, BytecodeLoader loader)
			throws InstrumentationException {
		// FIXME factor out these arg checks into an abstract instrumentor
		if (loader == null) {
			throw new IllegalArgumentException(
					"Argument `loader` cannot be null");
		}
		if (className == null) {
			throw new IllegalArgumentException(
					"Argument `className` cannot be null");
		}

		className = className.trim();

		if (className.length() == 0) {
			throw new IllegalArgumentException(
					"Argument `className` cannot be an empty");
		}

		try {
			byte[] bytecode = loader.loadBytecode(className);
			if (bytecode == null) {
				throw new CannotLoadBytecodeException(className);
			}

			{
				ClassReader reader = new ClassReader(bytecode);
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
				reader.accept(new NotNullInstrumentor(writer), 0);
				bytecode = writer.toByteArray();
			}
			{
				ClassReader reader = new ClassReader(bytecode);
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
				reader.accept(new NotEmptyInstrumentor(writer), 0);
				bytecode = writer.toByteArray();
			}
			return bytecode;

		} catch (Exception e) {
			// TODO message
			throw new InstrumentationException(e);
		}
	}

}
