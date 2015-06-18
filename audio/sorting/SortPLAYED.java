package audio.sorting;

import java.util.Comparator;

import audio.Song;

public class SortPLAYED implements Comparator<Song> {

	@Override
	public int compare(Song s1, Song s2) {
		if(s1.getTimesPlayed() < s2.getTimesPlayed())
			return 1;
		else if(s1.getTimesPlayed() > s2.getTimesPlayed())
			return -1;
		else
			return s1.getTitle().compareTo(s2.getTitle());
	}

}
