package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

/**
 * Created by iasatan on 2017.10.17..
 */

public class Person {
    //region variables
    private final Integer id;
    private final String name;
    private final Integer roomId;
    private final String title;
    private final Integer imageId;

    //endregion
    public Person(Integer id, String name, Integer roomId, Integer image, String title) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.title = title;
        imageId = image;
    }

    //region getters & setters
    public String getTitle() {
        return title;
    }

    public Integer getImageId() {
        return imageId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getRoomId() {
        return roomId;
    }


    //endregion
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) {
            return false;
        }
        if (roomId != null ? !roomId.equals(person.roomId) : person.roomId != null) {
            return false;
        }
        if (title != null ? !title.equals(person.title) : person.title != null) {
            return false;
        }
        return imageId != null ? imageId.equals(person.imageId) : person.imageId == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        return result;
    }
}
