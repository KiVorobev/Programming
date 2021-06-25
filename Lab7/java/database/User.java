package database;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    private List<SpaceMarines> spaceMarines;
    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        spaceMarines = new ArrayList<>();
    }

    public void removeSpaceMarine(SpaceMarines spaceMarine) {
        spaceMarines.remove(spaceMarine);
    }

    public List<SpaceMarines> getSpaceMarines() {
        return spaceMarines;
    }

    public void setSpaceMarine(List<SpaceMarines> spaceMarines) {
        this.spaceMarines = spaceMarines;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSpaceMarines(List<SpaceMarines> spaceMarines) {
        this.spaceMarines = spaceMarines;
    }

    @Override
    public String toString() {
        return "User: " + login;
    }
}
