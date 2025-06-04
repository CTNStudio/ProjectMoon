package ctn.project_moon.api;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 一个有序不重复序列
 * @author 尽
 */
public class UniqueList<T> extends ArrayList<T> {
	private final Set<T> set = new LinkedHashSet<>();

	@Override
	public boolean add(T item) {
		if (set.add(item)) {
			return super.add(item);
		}
		return false;
	}
}
