package ctn.project_moon.util;


import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class UniqueList<T> extends ArrayList<T> {
	@Override
	public boolean add(T t) {
		if (this.contains(t)) {
			return false;
		}

		return super.add(t);
	}

	@Override
	public void add(int index, T element) {
		if (this.contains(element)) {
			return;
		}

		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		c.forEach(this::add);
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return super.addAll(index, c);
	}

	@Override
	public void addFirst(T element) {
		if (this.contains(element)) {
			return;
		}
		super.addFirst(element);
	}

	@Override
	public void addLast(T element) {
		if (this.contains(element)) {
			return;
		}
		super.addLast(element);
	}

	public void unique() {
		LinkedHashSet<T> set = Sets.newLinkedHashSetWithExpectedSize(this.size());
		set.addAll(this);
		this.clear();
		this.addAll(set);
	}
}
