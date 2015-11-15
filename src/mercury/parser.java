package mercury;

import java.util.List;

public abstract class parser {
	/**
	 * @param str
	 * @return List of parsed pair or null if failed.
	 */
	abstract List<pair> parse(String str);
}
