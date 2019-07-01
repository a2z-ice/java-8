package stream.exercise;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import loop.Person;

public class PersonSpliterator implements Spliterator<Person> {

	
	private Spliterator<String> lineSpliterator;
	private String name;
	private int age;
	private String city;
	
	public PersonSpliterator(Spliterator<String> lineSpliterator) {
		this.lineSpliterator = lineSpliterator;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Person> action) {
		
		Predicate<Spliterator<String>> hasName = lsp -> lsp.tryAdvance(line -> name = line);
		Predicate<Spliterator<String>> hashAge = lsp -> lsp.tryAdvance(line -> age = Integer.valueOf(line));
		Predicate<Spliterator<String>> hasCity = lsp -> lsp.tryAdvance(line -> city = line);
		
		if(hasName.and(hashAge).and(hasCity).test(lineSpliterator)) {
			action.accept(new Person(name, age, city));
			return true;
		}
		return false;
	}

	@Override
	public Spliterator<Person> trySplit() {
		return null;
	}

	@Override
	public long estimateSize() {
		return lineSpliterator.estimateSize() / 3;
	}

	@Override
	public int characteristics() {
		return lineSpliterator.characteristics();
	}

}
