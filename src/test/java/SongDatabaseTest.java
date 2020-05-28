import models.Song;
import models.SongDatabase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SongDatabaseTest {
    @Test
    public void testAddDeleteSong() {
        SongDatabase database = new SongDatabase();
        Song songOne = new Song("SongOne", "1");
        Song songTwo = new Song("SongTwo", "2");

        database.addSong(songOne);
        Assert.assertTrue(database.getSongArchive().contains(songOne));

        database.addSong(songTwo);
        Assert.assertTrue(database.getSongArchive().contains(songTwo));

        database.deleteSong(songTwo);

        Assert.assertTrue(database.getSongArchive().contains(songOne));
        Assert.assertFalse(database.getSongArchive().contains(songTwo));
    }

    @Test
    public void testAddGetDeletePlaylist() {
        SongDatabase database = new SongDatabase();
        String playlistNameOne = "PlaylistOne";
        String playlistNameTwo = "PlaylistTwo";

        database.createPlaylist(playlistNameOne);
        Assert.assertTrue(database.getPlaylists().containsKey(playlistNameOne));

        database.createPlaylist(playlistNameTwo);
        Assert.assertTrue(database.getPlaylists().containsKey(playlistNameTwo));

        List<Song> songs = database.getPlaylist(playlistNameOne);
        Assert.assertFalse(songs.isEmpty());

        database.deletePlaylist(playlistNameTwo);

        Assert.assertTrue(database.getPlaylists().containsKey(playlistNameOne));
        Assert.assertFalse(database.getPlaylists().containsKey(playlistNameTwo));
    }

    @Test
    public void testAddDeleteSongFromPlaylist() {
        SongDatabase database = new SongDatabase();
        String playlistName = "PlaylistOne";
        Song songOne = new Song("SongOne", "1");
        Song songTwo = new Song("SongTwo", "2");

        database.createPlaylist(playlistName);
        Assert.assertTrue(database.getPlaylists().containsKey(playlistName));

        database.addSongToPlaylist(songOne, playlistName);
        Assert.assertTrue(database.getPlaylists().get(playlistName).contains(songOne));

        database.addSongToPlaylist(songTwo, playlistName);
        Assert.assertTrue(database.getPlaylists().get(playlistName).contains(songTwo));

        database.addSongToPlaylist(songTwo, playlistName);

        database.deleteSongFromPlaylist(songTwo, playlistName);

        Assert.assertTrue(database.getPlaylists().get(playlistName).contains(songOne));
        Assert.assertFalse(database.getPlaylists().get(playlistName).contains(songTwo));
    }

    @Test
    public void testFindingSongs() {
        SongDatabase database = new SongDatabase();
        Song songOne = new Song("SongOne", "1");
        Song songTwo = new Song("SongTwo", "2");

        database.addSong(songOne);
        database.addSong(songTwo);

        List<Song> foundSongs = database.findSongByName("SongOne");
        Assert.assertTrue(foundSongs.contains(songOne));

        foundSongs = database.findSongsByArtist("2");
        Assert.assertTrue(foundSongs.contains(songTwo));
    }
}