package com.mmoclient.util;

import java.util.Comparator;

import com.mmoclient.entities.Player;

public class IntComparable implements Comparator<Player>{
	@Override
	public int compare(Player o1, Player o2) {
		return ((o1.getKills())>(o2.getKills()) ? -1 : ((o1.getKills())==(o2.getKills()) ? ((o1.getDeaths()))<(o2.getDeaths()) ? -1 : 1 : 1));
	}
}
