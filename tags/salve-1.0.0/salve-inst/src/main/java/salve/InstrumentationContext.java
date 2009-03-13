package salve;

/**
 * Represents context in which the instrumentor runs
 * 
 * @author igor.vaynberg
 * 
 */
public class InstrumentationContext {

	private final BytecodeLoader loader;
	private final InstrumentorMonitor monitor;
	private final Scope scope;

	/**
	 * Constructor
	 * 
	 * @param loader
	 *            bytecode loader the instrumentor can use to access bytecode
	 * @param monitor
	 *            monitor that should be notified of any changes the
	 *            instrumentor makes
	 * @param scope
	 *            scope used to identify classes that are within instrumentation
	 *            scope
	 */
	public InstrumentationContext(BytecodeLoader loader, InstrumentorMonitor monitor, Scope scope) {
		if (loader == null) {
			throw new IllegalArgumentException("Argument `loader` cannot be null");
		}
		if (monitor == null) {
			throw new IllegalArgumentException("Argument `monitor` cannot be null");
		}
		if (scope == null) {
			throw new IllegalArgumentException("Argument `scope` cannot be null");
		}

		this.loader = loader;
		this.monitor = monitor;
		this.scope = scope;
	}

	/**
	 * @return bytecode loader the instrumentor can use to access bytecode
	 */
	public BytecodeLoader getLoader() {
		return loader;
	}

	/**
	 * @return monitor that should be notified of any changes the instrumentor
	 *         makes
	 */
	public InstrumentorMonitor getMonitor() {
		return monitor;
	}

	/**
	 * @return scope used to identify classes that are within instrumentation
	 *         scope
	 */
	public Scope getScope() {
		return scope;
	}

}
