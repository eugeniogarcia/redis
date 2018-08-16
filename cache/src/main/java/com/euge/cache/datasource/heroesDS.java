package com.euge.cache.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.euge.cache.model.Hero;

public class heroesDS {
	private final List<Hero> source;
	private int last;
	private final int RETRASO=1;

	public heroesDS(){
		source=new ArrayList<Hero>();
		source.add(new Hero.Builder().id(1).name("Pupa").createHero());
		source.add(new Hero.Builder().id(2).name("Nani").createHero());
		source.add(new Hero.Builder().id(3).name("Mausi").createHero());
		source.add(new Hero.Builder().id(4).name("Nico").createHero());
		last=4;
	}

	protected void retraso() {
		try {
			Thread.sleep(RETRASO*1000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Hero add(Hero hero) {
		retraso();
		do {
			last++;
		}while(source.stream().anyMatch(x-> {return (last==x.getId());}));

		final Hero val=new Hero.Builder().id(last).name(hero.getName()).createHero();

		source.add(val);

		return val;
	}

	public Hero update(Hero hero) {
		retraso();
		final Optional<Hero> val=source.stream().filter((Hero x)-> x.getId()==hero.getId()).findFirst();

		if(val.isPresent()) {
			source.remove(val.get());
			source.add(hero);
			return hero;
		}
		return null;
	}

	public boolean delete(Hero hero) {
		retraso();
		final Optional<Hero> val=source.stream().filter((Hero x)-> x.getId()==hero.getId()).findFirst();

		if(val.isPresent()) {
			source.remove(val.get());
			return true;
		}
		return false;
	}	

	public Hero find(int Id) {
		retraso();
		final Optional<Hero> val=source.stream().filter((Hero x)-> x.getId()==Id).findFirst();

		if(val.isPresent()) {
			return val.get();
		}
		return null;
	}	

	//son equivalentes
	@Cacheable(value="misHeroes",unless="#result==null")
	//@Cacheable("misHeroes")
	//@Cacheable(value="misHeroes",key="#id")
	//Condition se aplica a la entrada. Aqui decimos que para cachear el id tiene que ser menor que cinco
	//@Cacheable(value="misHeroes",key="#id",condition="#id<5")
	//unless se aplica a la respuesta. Aqui decimos que si el name es Nani, no se debe cachear
	//@Cacheable(value="misHeroes",key="#id",condition="#id<5",unless="#result.name==\"Nani\"")
	//@Cacheable(value="misHeroes",key="#id",condition="#id<5",unless="#result.name=='Nani'")
	//@Cacheable(value="misHeroes",key="#id",condition="#id<5",unless="#result==null")
	public Hero findCache(int Id) {
		retraso();
		final Optional<Hero> val=source.stream().filter((Hero x)-> x.getId()==Id).findFirst();

		if(val.isPresent()) {
			return val.get();
		}
		return null;
	}	

	public List<Hero> list() {
		retraso();
		return source;
	}	


	//Podemos usar mas de una cache
	//@Cacheable({"misHeroes1","misHeroes2"})
	//@Cacheable(value="misHeroes",cacheManager="largaDuracion")
	@Cacheable(value="misHeroes")
	public List<Hero> listCache() {
		retraso();
		return source;
	}	

	@CachePut(value="misHeroes",key="#hero.getId()",unless="#result==null")
	public Hero updateCache(Hero hero) {
		retraso();
		final Optional<Hero> val=source.stream().filter((Hero x)-> x.getId()==hero.getId()).findFirst();

		if(val.isPresent()) {
			source.remove(val.get());
			source.add(hero);
			return hero;
		}
		return null;
	}

}
