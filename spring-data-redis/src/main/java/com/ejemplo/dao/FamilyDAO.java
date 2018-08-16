package com.ejemplo.dao;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ejemplo.bean.Person;

@Repository
@Transactional
public class FamilyDAO {
	  private static final String KEY = "myfamily";
	  
	  @Resource(name="redisTemplate")
	  private SetOperations<String, Person> setOps;	  
	  
	  public void addFamilyMembers(Person... persons) {
		  setOps.add(KEY, persons);  	
	  }
	  public Set<Person> getFamilyMembers() {
		  return setOps.members(KEY);
	  }
	  public long getNumberOfFamilyMembers() {
		  return setOps.size(KEY);
	  }
	  public long removeFamilyMembers(Person... persons) {
		  return setOps.remove(KEY, (Object[])persons);
	  }	 
}
