package models;

import java.util.*;

public class SongDatabase {
    /**
     * A unique list of every song in the models.Song Database
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

    protected void setSongArchive(TreeSet<Song> songArchive) {
        this.songArchive = songArchive;
    }

    protected void setPlaylists(Map<String, List<Song>> playlists) {
        this.playlists = playlists;
    }

    public void addSong(Song newSong) {
        songArchive.add(newSong);
    }

    public void deleteSong(Song songToBeDeleted) {
        System.out.println(songArchive.contains(songToBeDeleted));
//        for (Song s : songArchive) {
//            System.out.println(s.equals(songToBeDeleted));
//        }
        if (songArchive.contains(songToBeDeleted)) {
            for (Song song : songArchive) {
                if (song.equals(songToBeDeleted)) {
                    for (String playlistName : playlists.keySet()) {
                        deleteSongFromPlaylist(song, playlistName);
                    }

                    songArchive.remove(song);

                    return; // TODO WRITE SHIT - CAUSE THIS IS NEEDED :P
                }
            }
        }
    }

    public void createPlaylist(String playlistName) throws IllegalArgumentException {
        if (playlists.containsKey(playlistName)) {
            throw new IllegalArgumentException("A playlist already exists with that the name: " + playlistName);
        }

        playlists.put(playlistName, new ArrayList<Song>());
    }

    public List<Song> getPlaylist(String playlistName) {
        if(playlists.containsKey(playlistName)) {
            return playlists.get(playlistName);
        }

        return null;
    }

    public void deletePlaylist(String playlistName) {
        if (playlists.containsKey(playlistName)) {
            playlists.remove(playlistName);
        }
    }

    /**
     * TODO FOR HOMEWORK: Complete this method to add a song to the playlist
     *                    NOTE: You MUST handle the case where a playlist with the given name doesn't exist and when the song isn't in the archive
     *                          This can be done a couple ways, but we recommend just creating the playlist/adding to the archives if it doesn't exist
     *                          Don't worry about duplicate songs you can have repeated songs in the same playlist
     * Adds a song to the playlist
     * @param newSong The new song
     * @param playlistName The playlist name
     */
    public void addSongToPlaylist(Song newSong, String playlistName) {
        // TODO DUSTIN: REMOVE
        if (!playlists.containsKey(playlistName)) {
            createPlaylist(playlistName);
        }

        if (!songArchive.contains(newSong)) {
            songArchive.add(newSong);
        }

        List<Song> songs = playlists.get(playlistName);
        songs.add(newSong);
        playlists.put(playlistName, songs);
    }

    public void deleteSongFromPlaylist(Song songToBeDeleted, String playlistName) {
        if (playlists.containsKey(playlistName)) {
            List<Song> listOfPlaylistSongs = playlists.get(playlistName);

            if (listOfPlaylistSongs.contains(songToBeDeleted)) {
                for (int i = listOfPlaylistSongs.size() - 1; i >= 0; i--) {
                    if (songToBeDeleted.equals(listOfPlaylistSongs.get(i))) {
                        playlists.get(playlistName).remove(i);
                    }
                }
            }
        }
    }

    /**
     * TODO FOR HOMEWORK: Complete this method
     * Returns a List of models.Song object from archive, and an empty list if no songs are found
     * @param songName The song name
     * @return The List of models.Song object, and an empty list if no songs are found
     */
    public List<Song> findSongByName(String songName) {
        // TODO DUSTIN: REMOVE
        List<Song> matchedSongs = new ArrayList<Song>();

        for (Song song : songArchive) {
            if (song.getName().equals(songName)) {
                matchedSongs.add(song);
            }
        }

        return matchedSongs;
    }

    /**
     * TODO FOR HOMEWORK: Complete this method
     * Returns a List of models.Song object from archive, and an empty list if no songs from the artist are found
     * @param artist The artist
     * @return The List of models.Song object, and an empty list if no songs from the artist are found
     */
    public List<Song> findSongsByArtist(String artist) {
        // TODO DUSTIN: REMOVE
        List<Song> matchedSongs = new ArrayList<Song>();

        for (Song song : songArchive) {
            if (song.getArtist().equals(artist)) {
                matchedSongs.add(song);
            }
        }

        return matchedSongs;
    }

    public static void main(String[] args) {
        SongDatabase db = new SongDatabase();

        db.addSong(new Song("TEST NAME", "TEST ART"));
        System.out.println(db.getSongArchive().size());

        System.out.println(db.songArchive.contains(new Song("TEST NAME", "TEST ART")));
        db.deleteSong(new Song("TEST NAME", "TEST ART"));
        System.out.println(db.songArchive.contains(new Song("TEST NAME", "TEST ART")));


//        db.addSong(new Song("TEST NAME", "TEST ART"));
//        System.out.println(db.getSongArchive().size());
    }
}
