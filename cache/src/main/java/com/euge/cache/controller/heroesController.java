package com.euge.cache.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.euge.cache.datasource.heroesDS;
import com.euge.cache.model.Hero;

@RestController
@RequestMapping(value = "/")
public class heroesController {

	@Autowired
	heroesDS servicio;

	@RequestMapping(value = "/heroes",produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Hero>> getHeroes(){
		return new ResponseEntity<List<Hero>>(servicio.list(),HttpStatus.OK);
	}


	@RequestMapping(value = "/heroes/{id}",produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Hero> getHero(@PathVariable("id") Integer id){
		final Hero resp=servicio.find(id);
		if(resp==null) {
			return new ResponseEntity<Hero>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Hero>(resp,HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/heroes",produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Hero> addHero(@RequestBody Hero hero){
		final Hero val=servicio.add(hero);
		if(val!=null) {
			return new ResponseEntity<Hero>(val,HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<Hero>(HttpStatus.BAD_REQUEST); 
		}
	}

	@RequestMapping(value = "/heroes",produces = { "application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Hero> updateHero(@RequestBody Hero hero){
		if(servicio.update(hero)!=null) {
			return new ResponseEntity<Hero>(hero,HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<Hero>(hero,HttpStatus.BAD_REQUEST); 
		}
	}

	@RequestMapping(value = "/heroes/{id}",produces = { "application/json" },method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteHero(@PathVariable int id){
		final Hero resp=servicio.find(id);
		if(resp==null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		if(servicio.delete(resp)) {
			return new ResponseEntity<Void>(HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); 
		}
	}

	//
	//
	//Methods with cache on
	//
	//

	//Podemos poner la anotación aqui porque este metodo no retorna una EntityResponse
	//Podemos usar mas de una cache
	//Limpia caches
	//@CacheEvict(value={"misHeroes1","misHeroes2"}, allEntries=true)
	@CacheEvict(value="misHeroes", allEntries=true)
	@RequestMapping(value = "/cache/heroes/limpia",produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity limpiaCache(){
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//Si puesieramos aqui la anotación @Cacheable fallaria, porque redis no puede serializar el EntityResponse
	@RequestMapping(value = "/cache/heroes",produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Hero>> getCachedHeroes(){
		return new ResponseEntity<List<Hero>>(servicio.listCache(),HttpStatus.OK);
	}

	//Si puesieramos aqui la anotación @Cacheable fallaria, porque redis no puede serializar el EntityResponse
	@RequestMapping(value = "/cache/heroes/{id}",produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Hero> getCachedHero(@PathVariable("id") Integer id){
		final Hero resp=servicio.findCache(id);
		if(resp==null) {
			return new ResponseEntity<Hero>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Hero>(resp,HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/cache/heroes",produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Hero> addCachedHero(@RequestBody Hero hero){
		return addHero(hero);
	}

	//Si puesieramos aqui la anotación @Cacheable fallaria, porque redis no puede serializar el EntityResponse
	@RequestMapping(value = "/cache/heroes",produces = { "application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Hero> updateCachedHero(@RequestBody Hero hero){
		if(servicio.updateCache(hero)!=null) {
			return new ResponseEntity<Hero>(hero,HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<Hero>(hero,HttpStatus.BAD_REQUEST); 
		}
	}

	//equivalentes
	@CacheEvict(value="misHeroes")
	//@CacheEvict(value="misHeroes", key="#id")
	@RequestMapping(value = "/cache/heroes/{id}",produces = { "application/json" },method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCachedHero(@PathVariable int id){
		return deleteHero(id);
	}

}
