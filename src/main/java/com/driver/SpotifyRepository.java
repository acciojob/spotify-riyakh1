package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User newUser = new User(name,mobile);
        users.add(newUser);
        return newUser;
    }

    public Artist createArtist(String name) {
        Artist newArtist = new Artist(name);
        newArtist.setLikes(0);
        artists.add(newArtist);
        return newArtist;
    }

    public Album createAlbum(String title, String artistName) {
        Artist artist1 = null;
        for(Artist artist : artists) {
            if (Objects.equals(artist.getName(), artistName)) {
                artist1 = artist;
                break;
            }
        }
        if(artist1 == null){
            artist1 = createArtist(artistName);

            Album newAlbum = new Album(title);
            newAlbum.setReleaseDate(new Date());

            albums.add(newAlbum);

            List<Album> list = new ArrayList<>();
            list.add(newAlbum);

            artistAlbumMap.put(artist1,list);
            return newAlbum;
        }
        else{
            Album newAlbum = new Album(title);
            newAlbum.setReleaseDate(new Date());

            albums.add(newAlbum);

            List<Album> list = new ArrayList<>();
            if(list == null){
                list = new ArrayList<>();
            }
            list.add(newAlbum);
            artistAlbumMap.put(artist1,list);
            return newAlbum;
        }
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Album album = null;
        for(Album album1:albums){
            if(album1.getTitle().equals(albumName)){
                album=album1;
                break;
            }
        }
        if(album==null)
            throw new Exception("Album does not exist");
        else {
            Song song = new Song();
            song.setTitle(title);
            song.setLength(length);
            song.setLikes(0);

            songs.add(song);

//            List<Song> l = albumSongMap.get(album);
//            l.add(song);
//            albumSongMap.put(album,l);

            if(albumSongMap.containsKey(album)){
                List<Song> l = albumSongMap.get(album);
                l.add(song);
                albumSongMap.put(album,l);
            }else{
                List<Song> songList = new ArrayList<>();
                songList.add(song);
                albumSongMap.put(album,songList);
            }

            return song;
        }
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        User user = null;
        for(User user1 :users){
            if(user1.getMobile()== mobile){
                user =user1;
                break;
            }
        }
        if(user ==null){
            throw new Exception("User does not exist");
        }
        else{
            Playlist playlist = new Playlist();
            playlist.setTitle(title);
            playlists.add(playlist);

            List<Song> list = new ArrayList<>();
            for(Song song :songs){
                if(song.getLength()==length){
                    list.add(song);
                }
            }
            playlistSongMap.put(playlist,list);
            List<User> list1 = new ArrayList<>();
            list1.add(user);
            playlistListenerMap.put(playlist,list1);
            creatorPlaylistMap.put(user,playlist);
            userPlaylistMap.get(user);

            if(userPlaylistMap.containsKey(user)){
                List<Playlist> userPlayList = userPlaylistMap.get(user);
                userPlayList.add(playlist);
                userPlaylistMap.put(user,userPlayList);
            }else{
                List<Playlist> plays = new ArrayList<>();
                plays.add(playlist);
                userPlaylistMap.put(user,plays);
            }

            return playlist;
        }
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        User user = null;
        for(User user1 : users){
            if(user1.getMobile() == mobile){
                user = user1;
                break;
            }
        }

        if(user == null){
            throw new Exception("User does not exist");
        }
        else{
            Playlist playlist = new Playlist();
            playlist.setTitle(title);
            playlists.add(playlist);

            List<Song> list = new ArrayList<>();
            for(Song song : songs){
                if(songTitles.contains(song.getTitle())){
                    list.add(song);
                }
            }
            playlistSongMap.put(playlist ,list);

            List<User> list1 = new ArrayList<>();
            list1.add(user);
            playlistListenerMap.put(playlist,list1);

            creatorPlaylistMap.put(user,playlist);


            if(userPlaylistMap.containsKey(user)){
                List<Playlist> userPlayList = userPlaylistMap.get(user);
                userPlayList.add(playlist);
                userPlaylistMap.put(user,userPlayList);
            }else{
                List<Playlist> plays = new ArrayList<>();
                plays.add(playlist);
                userPlaylistMap.put(user,plays);
            }

            return playlist;
        }
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

    }

    public String mostPopularArtist() {
    }

    public String mostPopularSong() {
    }
}
