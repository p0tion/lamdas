import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda {

    public static List<String> findLongTracks(List<Album> albums) {
        List<String> trackNames = new ArrayList<>();
        for (Album album : albums) {
            for (Track track : album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    public static List<String> findLongTracksWithForEachStream(List<Album> albums) {
        List<String> trackNames = new ArrayList<>();
        albums.forEach(album -> {
            album.getTrackList().forEach(track -> {
                if (track.getLength() > 60) {
                    trackNames.add(track.getName());
                }
            });
        });
        return trackNames;
    }

    public static List<String> findLongTracksWithForEachMap(List<Album> albums) {
        List<String> trackNames = new ArrayList<>();
        albums.forEach(album -> {
            album.getTrackList()
                    .stream()
                    .filter(track -> track.getLength() > 60)
                    .map(Track::getName)
                    .forEach(name -> trackNames.add(name));
        });
        return trackNames;
    }

    public static List<String> findLongTracksWithFlatMapFilterMap(List<Album> albums) {
        List<String> trackNames = new ArrayList<>();
        albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > 60)
                .map(Track::getName)
                .forEach(trackNames::add);
        return trackNames;
    }

    public static List<String> findLongTracksWithFlatMapFilterMapCollect(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .filter(track -> track.getLength() > 60)
                .map(Track::getName)
                .collect(Collectors.toList());
    }

    // Chapter3 Exercises
    //Ch3.1.a
    public static int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, Integer::sum);
    }

    //Ch3.1.b
    public static List<String> getNamesAndOrigins(List<Artist> artists) {
        return artists.stream()
                .map(artist -> artist.getName() + " - " + artist.getOrigin())
                .collect(Collectors.toList());
    }

    //Ch3.1.c
    public static List<Album> getAlbumsWithAtMostThreeTracks(List<Album> albums) {
        return albums.stream()
                .filter(album -> album.getTrackList().size() < 4)
                .collect(Collectors.toList());
    }

    //Ch3.2
    public static int externalIteration(List<Artist> artists) {
        int totalMembers = 0;
        for (Artist artist : artists) {
            Stream<Artist> members = artist.getMembers().stream();
            totalMembers += members.count();
        }
        return totalMembers;
    }

    public static int internalIteration(List<Artist> artists) {
        return (int) artists.stream()
                .flatMap(artist -> artist.getMembers().stream())
                .count();
    }

    //Ch3.6
    public static int countLowerCaseLettersMine(String string) {
        return (int) Arrays.stream(string.split(""))
                .filter(letter -> letter.matches("[a-z]"))
                .count();
    }

    public static int countLowerCaseLettersTheir(String string) {
        return (int) string.chars()
                .filter(Character::isLowerCase)
                .count();
    }

    //Ch3.7
    public static Optional<String> findMostLowerCaseLettersString(List<String> strings) {
        return strings.stream()
                .max(Comparator.comparing(Lambda::countLowerCaseLettersTheir));
    }
    
    //Ch4.1
    interface Perfomance {
        String getName();

        Stream<Artist> getMusicians();

        default Stream<Artist> getAllMusicians() {
            return getMusicians()
                    .flatMap(artist -> Stream.concat(Stream.of(artist), artist.getMembers().stream()));
        }
    }

    //average
    public static double averageNumberOfTracks(List<Album> albums) {
        return albums.stream()
                .collect(Collectors.averagingInt(album -> album.getTrackList().size()));
    }

    //partitioningBy
    public static Map<Boolean, List<Artist>> divideToBandsAndSoloArtists(List<Artist> artists) {
        return artists.stream().collect(Collectors.partitioningBy(Artist::isSolo));
    }

    //groupingBy
    public static Map<Integer, List<Album>> groupByAlbumSize(List<Album> albums) {
        return albums.stream().collect(Collectors.groupingBy(album -> album.getTrackList().size()));
    }

    //stringJoining
    public static String joinTracks(List<Album> albums) {
        return albums.stream()
                .flatMap(album -> album.getTrackList().stream())
                .map(Track::getName)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public static void main(String[] args) {

        Track track1_1 = new Track("track1_1", 15);
        Track track1_2 = new Track("track1_2", 68);
        Track track1_3 = new Track("track1_3", 78);
        Track track1_4 = new Track("track1_4", 16);
        Track track1_5 = new Track("track1_5", 111);
        Track track1_6 = new Track("track1_6", 45);
        Track track2_1 = new Track("track2_1", 546);
        Track track2_2 = new Track("track2_2", 48);
        Track track2_3 = new Track("track2_3", 211);
        Track track2_4 = new Track("track2_4", 15);
        Track track3_1 = new Track("track3_1", 49);
        Track track3_2 = new Track("track3_2", 256);
        Track track3_3 = new Track("track3_3", 212);
        Track track3_4 = new Track("track3_4", 54);

        Album album1 = new Album();
        album1.setName("album1");
        Album album2 = new Album();
        album2.setName("album2");
        Album album3 = new Album();
        album3.setName("album3");

        album1.getTrackList().addAll(Arrays.asList(track1_1, track1_2, track1_3, track1_4, track1_5, track1_6));
        album2.getTrackList().addAll(Arrays.asList(track2_1, track2_2, track2_3, track2_4));
        album3.getTrackList().addAll(Arrays.asList(track3_1, track3_2, track3_3, track3_4));

        System.out.println(findLongTracks(Arrays.asList(album1, album2, album3)));
        System.out.println(findLongTracksWithForEachStream(Arrays.asList(album1, album2, album3)));
        System.out.println(findLongTracksWithForEachMap(Arrays.asList(album1, album2, album3)));
        System.out.println(findLongTracksWithFlatMapFilterMap(Arrays.asList(album1, album2, album3)));
        System.out.println(findLongTracksWithFlatMapFilterMapCollect(Arrays.asList(album1, album2, album3)));

        //Chapter3 checking
        //Ch3.1.a
        System.out.println();
        System.out.println();
        System.out.println("//Chapter3\n  //Ch3.1.a");
        System.out.println(addUp(Stream.of(1, 2, 5, 2)));

        //Ch3.1.b
        Artist artist3_1_1 = new Artist("vasya", "kiev");
        Artist artist3_1_2 = new Artist("petya", "lviv");
        Artist artist3_1_3 = new Artist("roma", "kamenskoe");
        System.out.println("\n  //Ch3.1.b");
        System.out.println(getNamesAndOrigins(Arrays.asList(artist3_1_1, artist3_1_2, artist3_1_3)));

        //Ch3.1.c
        Album withThreeTracks = new Album();
        withThreeTracks.setName("withThreeTracks");
        Album withTwoTracks = new Album();
        withTwoTracks.setName("withTwoTracks");
        Album withFourTracks = new Album();
        withFourTracks.setName("withFourTracks");

        withTwoTracks.getTrackList().addAll(Arrays.asList(track1_1, track1_3));
        withThreeTracks.getTrackList().addAll(Arrays.asList(track2_1, track2_3, track2_4));
        withFourTracks.getTrackList().addAll(Arrays.asList(track3_1, track3_3, track3_2, track1_2));

        System.out.println("\n  //Ch3.1.c");

        getAlbumsWithAtMostThreeTracks(Arrays.asList(withFourTracks, withThreeTracks, withTwoTracks))
                .forEach(album -> System.out.print(album.getName() + " "));

        //Ch3.2
        Artist artist3_2_1 = new Artist("vasya", "kiev");
        Artist artist3_2_2 = new Artist("petya", "lviv");
        Artist artist3_2_3 = new Artist("roma", "kamenskoe");
        artist3_2_1.getMembers().add(artist3_2_3);
        artist3_2_2.getMembers().add(artist3_2_1);

        System.out.println("\n  //Ch3.2");
        System.out.println("externalIteration: " + externalIteration(Arrays.asList(artist3_2_1, artist3_2_2, artist3_2_3)));
        System.out.println("internalIteration: " + internalIteration(Arrays.asList(artist3_2_1, artist3_2_2, artist3_2_3)));

        //Ch3.6
        System.out.println("\n  //Ch3.6");
        System.out.println(countLowerCaseLettersMine("hint: look at the chars method on String"));
        System.out.println(countLowerCaseLettersTheir("hint: look at the chars method on String"));

        //Ch3.7
        System.out.println("\n  //Ch3.7");
        System.out.println(findMostLowerCaseLettersString(Arrays.asList("DDDD", "AAAA")).get());
        System.out.println(findMostLowerCaseLettersString(Arrays.asList("abcD", "fdsdsfs", "asd")).get());

        //average
        System.out.println("\n  //average");
        System.out.println(averageNumberOfTracks(Arrays.asList(album1, album2, album3)));

        //partitioningBy
        System.out.println("\n  //partitioningBy");
        System.out.println(divideToBandsAndSoloArtists(Arrays.asList(artist3_2_1, artist3_2_3, artist3_2_2)));

        //groupingBy
        System.out.println("\n  //groupingBy");
        System.out.println(groupByAlbumSize(Arrays.asList(album1, album2, album3)));

        //joining
        System.out.println("\n  //joining");
        System.out.println(joinTracks(Arrays.asList(album1, album2, album3)));

    }

}
