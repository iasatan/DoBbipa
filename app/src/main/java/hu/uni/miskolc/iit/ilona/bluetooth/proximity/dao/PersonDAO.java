package hu.uni.miskolc.iit.ilona.bluetooth.proximity.dao;

import java.util.Collection;

import hu.uni.miskolc.iit.ilona.bluetooth.proximity.model.Person;

/**
 * Created by iasatan on 2017.11.13..
 */

public interface PersonDAO {
    public void addPerson(Person person);

    public Person getPerson(int id);

    public Collection<Person> getAllPeople();

    public Collection<Person> getPeopleInRoom(int roomId);

    public int getPeopleCount();

    public int updatePerson(Person person);

    public void deletePerson(int id);
}
