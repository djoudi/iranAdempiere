package org.adempiere.jbpm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Actor implements Serializable{

	private static Actor actor;
	
	private Actor(){
		
	}
	
	public static synchronized Actor getInstance(){
		if(actor == null){
			actor = new Actor();
		}
		return actor;
	}
	
	private String id;
	private Set<String> groupActorIds = new HashSet<String>() {
		@Override
		public boolean add(String o) {
			boolean dirty = super.add(o);
			return dirty;
		}

		@Override
		public void clear() {
			super.clear();
		}

		@Override
		public boolean remove(Object o) {
			boolean dirty = super.remove(o);
			return dirty;
		}

		@Override
		public Iterator<String> iterator() {
			final Iterator<String> it = super.iterator();
			return new Iterator<String>() {

				public boolean hasNext() {
					return it.hasNext();
				}

				public String next() {
					return it.next();
				}

				public void remove() {
					it.remove();
				}

			};
		}
	};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<String> getGroupActorIds() {
		return groupActorIds;
	}

	@Override
	public String toString() {
		return "Actor(" + id + ")";
	}

}
