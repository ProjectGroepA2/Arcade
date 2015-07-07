package audio.sorting;

import java.util.Comparator;

import audio.Song;

public class SortSCORE implements Comparator<Song> {

	public int compare(Song s1, Song s2) {
		if(s1.getHighestScore() < s2.getHighestScore())
			return 1;
		else if(s1.getHighestScore() > s2.getHighestScore())
			return -1;
		else
			return s1.getTitle().compareTo(s2.getTitle());
	}

}
