import java.util.ArrayList;
import java.util.List;

public class Album {

    private String name;
    private List<Track> trackList;
    private List<Artist> musicians;

    public Album() {
    }

    public Album(String name, List<Track> trackList, List<Artist> musicians) {
        this.name = name;
        this.trackList = trackList;
        this.musicians = musicians;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTrackList() {
        if (null == trackList) {
            trackList = new ArrayList<>();
        }
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public List<Artist> getMusicians() {
        if (null == musicians) {
            musicians = new ArrayList<>();
        }
        return musicians;
    }

    public void setMusicians(List<Artist> musicians) {
        this.musicians = musicians;
    }
}
