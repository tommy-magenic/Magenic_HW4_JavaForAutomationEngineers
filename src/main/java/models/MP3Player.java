package models;

import resources.OptionStrings;

import java.util.*;

public class MP3Player {
    private SongDatabase songDatabase;

    private Song currentlyPlaying;

    private List<Song> songQueue;

    private Scanner scanner;

    public MP3Player() {
        this.songDatabase = new SongDatabase();
        currentlyPlaying = null;
        songQueue = new ArrayList<Song>();
        this.scanner = new Scanner(System.in);
    }

    public SongDatabase getSongDatabase() {
        return songDatabase;
    }

    protected void setSongDatabase(SongDatabase songDatabase) {
        this.songDatabase = songDatabase;
    }

    private void mainMenu() {
        int userChoice = displayOptionsAndWaitForValidOption(OptionStrings.MAIN_MENU_OPTIONS);
        String userChoiceText = OptionStrings.MAIN_MENU_OPTIONS[userChoice];

        if (userChoiceText.equals("Play")) {
            playMenu();
        } else if (userChoiceText.equals("Manage Playlists")) {
            managePlaylistsMenu();
        } else if (userChoiceText.equals("Manage Songs")) {
            manageSongsMenu();
        } else {
            System.out.println("ENDING PROGRAM...");
            System.out.println("Thank you for using this MP3 Player!");
        }
    }

    private void playMenu() {
        int userChoice = displayOptionsAndWaitForValidOption(OptionStrings.PLAY_OPTIONS);
        String userChoiceText = OptionStrings.PLAY_OPTIONS[userChoice];

        if (userChoiceText.equals("Play Song")) {
            String songName = getInputFromPrompt("What song would you like to play:");
            Song songToPlay = findSong(songName);
            playSong(songToPlay);
        } else if (userChoiceText.equals("Play Playlist")) {
            String playlistName = getInputFromPrompt("What playlist would you like to play:");
            playPlaylist(playlistName);
        }

        System.out.println("Returning to Main Menu...");
        mainMenu();
    }

    private void managePlaylistsMenu() {
        int userChoice = displayOptionsAndWaitForValidOption(OptionStrings.MANAGE_PLAYLISTS_OPTIONS);
        String userChoiceText = OptionStrings.MANAGE_PLAYLISTS_OPTIONS[userChoice];

        if (userChoiceText.equals("Create Playlist")) {
            String playlistName = getInputFromPrompt("What should the playlist be called:");
            tryCreatePlaylist(playlistName);
        } else if (userChoiceText.equals("Show Playlists")) {
            printMap(songDatabase.getPlaylists());
        } else if (userChoiceText.equals("Add Song to Playlist")) {
            String playlistName = getInputFromPrompt("Playlist Name:");
            String songName = getInputFromPrompt("Song Name:");
            String artist = getInputFromPrompt("Artist of the song " + songName + ":");
            Song songToAdd = new Song(songName, artist);
            songDatabase.addSongToPlaylist(songToAdd, playlistName);
        } else if (userChoiceText.equals("Delete Song from Playlist")) {
            String playlistName = getInputFromPrompt("Playlist Name:");
            String songName = getInputFromPrompt("Song Name:");
            Song songToRemoved = findSong(songName);
            songDatabase.deleteSongFromPlaylist(songToRemoved, playlistName);
        } else if (userChoiceText.equals("Delete Playlist")) {
            String playlistName = getInputFromPrompt("Playlist Name:");
            songDatabase.deletePlaylist(playlistName);
        }

        System.out.println("Returning to Main Menu...");
        mainMenu();
    }

    private void manageSongsMenu() {
        int userChoice = displayOptionsAndWaitForValidOption(OptionStrings.MANAGE_SONGS_OPTIONS);
        String userChoiceText = OptionStrings.MANAGE_SONGS_OPTIONS[userChoice];

        if (userChoiceText.equals("Show Song Library")) {
            List<Song> songs = new ArrayList<>(songDatabase.getSongArchive());
            printSongs(songs);
        } else if (userChoiceText.equals("Add Song")) {
            String songName = getInputFromPrompt("Song Name:");
            String artist = getInputFromPrompt("Artist of the song " + songName + ":");
            Song songToAdd = new Song(songName, artist);
            songDatabase.addSong(songToAdd);
        } else if (userChoiceText.equals("Delete Song")) {
            String songName = getInputFromPrompt("What song would you like to delete:");
            Song songToDelete = findSong(songName);
            songDatabase.deleteSong(songToDelete);
            System.out.println("Deleted Song: " + songToDelete);
        }

        System.out.println("Returning to Main Menu...");
        mainMenu();
    }

    private Song findSong(String songName) {
        List<Song> foundSongs = songDatabase.findSongByName(songName);
        if (foundSongs.isEmpty()) {
            System.out.println("SONG: " + songName +" was not found, returning to main menu:");
            return null;
        }
        else if (foundSongs.size() == 1) {
            return foundSongs.get(0);
        }
        else {
            int selectedSongIndex = displayOptionsAndWaitForValidOption(foundSongs);
            return foundSongs.get(selectedSongIndex);
        }
    }

    private boolean playSong(Song song) {
        boolean playNextSong = false;

        if (song != null) {
            currentlyPlaying = song;
            System.out.println("CURRENTLY PLAYING: " + song);
            int userChoice = displayOptionsAndWaitForValidOption(OptionStrings.PLAYING_OPTIONS);
            String userChoiceText = OptionStrings.PLAYING_OPTIONS[userChoice];
            if (userChoiceText == "Next") {
                playNextSong = true;
            }
        }

        return playNextSong;
    }

    private void playPlaylist(String playlistName) {
        List<Song> playlistSongs = songDatabase.getPlaylist(playlistName);
        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println("The playlist named '" + playlistName + "' does not exists or is an empty playlist");
            return;
        }

        songQueue = playlistSongs;
        boolean playNextSong = true;

        while(playNextSong) {
            if (songQueue.isEmpty()) {
                break;
            }

            Song songToPlay = songQueue.get(0);
            songQueue.remove(0);
            playNextSong = playSong(songToPlay);
        }

        System.out.println("Stopping Playlist...");
    }

    private void tryCreatePlaylist(String playlistName) {
        try {
            songDatabase.createPlaylist(playlistName);
            System.out.println("New Playlist Created: " + playlistName);
        } catch (Exception e) { // HOMEWORK? Update the exception?
            System.out.println("A playlist already exists with the name: " + playlistName + ". A unique name is required.");
        }
    }

    private void printList(String[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.println(i + ". " + list[i]);
        }
    }

    private void printMap(Map<String, List<Song>> map) {
        for (String key : map.keySet()) {
            System.out.println("- " + key);
            printSongs(map.get(key));
        }

    }

    private void printSongs(List<Song> songs) {
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(i + ". " + songs.get(i));
        }
    }

    // Move to Utils? and add packages?
    private void printOptions(String[] options) {
        printList(options);
        System.out.println("Enter the number of your selected option:");
    }

    private int displayOptionsAndWaitForValidOption(String[] options) {
        int option = -1;
        printOptions(options);

        do {
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
                if (option >= 0 && option <= options.length) {
                    return option;
                }

                printOptions(options);
            } catch(NumberFormatException e) {
                printOptions(options);
            }
        } while (true);
    }

    private int displayOptionsAndWaitForValidOption(List<Song> songOptions) {
        String[] songText = new String[songOptions.size()];
        for (int i = 0; i < songOptions.size(); i++) {
            songText[i] = songOptions.get(i).toString();
        }

        return displayOptionsAndWaitForValidOption(songText);
    }

    private String getInputFromPrompt(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        MP3Player mp3 = new MP3Player();
        // Songs
        mp3.songDatabase.addSong(new Song("test", "art1"));
        mp3.songDatabase.addSong(new Song("test", "art2"));
        mp3.songDatabase.addSong(new Song("test", "art3"));
        mp3.songDatabase.addSong(new Song("test", "art4"));
        mp3.songDatabase.addSong(new Song("test", "art5"));
        mp3.songDatabase.addSong(new Song("test", "art6"));
        mp3.songDatabase.addSong(new Song("test", "art7"));
        mp3.songDatabase.addSong(new Song("test", "art8"));
        mp3.songDatabase.addSong(new Song("test", "art9"));
        mp3.songDatabase.addSong(new Song("test", "art10"));
        mp3.songDatabase.addSong(new Song("test", "art11"));
        mp3.songDatabase.addSong(new Song("test", "art12"));

        System.out.println("NUMBER OF SONGS: " + mp3.songDatabase.getSongArchive().size());

        // Does the set archive contain them NOTE THEY SHOULD BE ALL TRUE BUT THEY AREN'T
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art1")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art2")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art3")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art4")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art5")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art6")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art7")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art8")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art9")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art10")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art11")));
        System.out.println(mp3.songDatabase.getSongArchive().contains(new Song("test", "art12")));
    }
}
