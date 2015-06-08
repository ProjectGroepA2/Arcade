package audio.sorting;

import java.util.Comparator;

import audio.Song;

public class SortPLAYED implements Comparator<Song> {

	@Override
	public int compare(Song s1, Song s2) {
		return s1.getAuthor().compareTo(s2.getAuthor());
	}

}
