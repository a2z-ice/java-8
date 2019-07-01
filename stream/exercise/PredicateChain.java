package stream.exercise;

import java.util.function.Function;
import java.util.function.Predicate;

public interface PredicateChain<T> extends Predicate<T>{
	
	static <U>PredicateChain<U> predecating(Function<U, Boolean> f) {
		return (t) -> Boolean.TRUE.equals(f.apply(t));
	}
	
}
