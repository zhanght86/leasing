package org.jbpm.pvm.internal.repository;

import java.util.HashSet;
import java.util.Set;

public class JBPMCACHE {
	protected static Set<RepositoryCache> repositoryCacheSet = new HashSet<RepositoryCache>();

	public static void setRepositoryCache(RepositoryCache repositoryCache) {
		repositoryCacheSet.add(repositoryCache);
	}

	public static void clean() {
		for (RepositoryCache s : repositoryCacheSet) {
			if (s != null) {
				try {
					s.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
