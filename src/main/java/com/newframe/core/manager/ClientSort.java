package com.newframe.core.manager;

import com.newframe.core.vo.Client;

import java.util.Comparator;

public class ClientSort implements Comparator<Client> {

	
	public int compare(Client prev, Client now) {
		return (int) (now.getLogindatetime().getTime()-prev.getLogindatetime().getTime());
	}

}
