import java.util.*;

public class SongDatabase {
    /**
     * A unique list of every song in the Song Database
     */
    private Set<Song> songArchive;

    private Map<String, List<Song>> playlists;

    public SongDatabase() {
        songArchive = new TreeSet<Song>();
        playlists = new HashMap<String, List<Song>>();
    }

    public Set<Song> getSongArchive() {
        return songArchive;
    }

    public Map<String, List<Song>> getPlaylists() {
        return playlists;
    }

    protected void setSongArchive(Set<Song> songArchive) {
        this.songArchive = songArchive;
    }

    protected void setPlaylists(Map<String, List<Song>> playlists) {
        this.playlists = playlists;
    }

    public void addSong(Song newSong) {
        songArchive.add(newSong);
    }

    public void deleteSong(Song songToBeDeleted) {
        for (Song song : songArchive) {
            if (song.equals(songToBeDeleted)) {
                for (String playlistName : playlists.keySet()) {
                    deleteSongFromPlaylist(playlistName, song);
                }

                songArchive.remove((song));
            }
        }
    }

    public void addPlaylist() {

    }

    public void deletePlaylist() {

    }

    public void addSongToPlaylist() {

    }

    public void deleteSongFromPlaylist(String playlistName, Song songToBeDeleted) {
        if (playlists.containsKey(playlistName)) {
            List<Song> listOfPlaylistSongs = playlists.get(playlistName);

            for (int i = listOfPlaylistSongs.size() - 1; i >= 0; i--) {
                if (songToBeDeleted.equals(listOfPlaylistSongs.get(i))) {
                    playlists.get(playlistName).remove(i);
                }
            }
        }
    }

    public Song findSongByName() {
        return null;
    }

    public List<Song> findSongsByArtist() {
        return null;
    }

    public static void main(String[] args) {
        SongDatabase db = new SongDatabase();

        db.addSong(new Song("TEST NAME", "TEST ART"));
        System.out.println(db.getSongArchive().size());

        System.out.println(db.songArchive.contains(new Song("TEST NAME", "TEST ART")));
    }
}
