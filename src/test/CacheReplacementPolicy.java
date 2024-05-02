package test;

public interface CacheReplacementPolicy{
	void add(String word); //Add into cache
	String remove(); //Remove from cache
}