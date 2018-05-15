import java.util.HashSet;
import java.util.Set;

public class Artist {

    private String name;
    private Set<Artist> members;
    private String origin;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, String origin) {
        this.name = name;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Artist> getMembers() {
        if (null == members) {
            members = new HashSet<>();
        }
        return members;
    }

    public void setMembers(Set<Artist> members) {
        this.members = members;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isFrom(String city) {
        return this.origin.toLowerCase().equals(city.toLowerCase());
    }

    public boolean isSolo() {
        return members.isEmpty();
    }
}
