package dk.easv.friendsv2.Model;

import java.util.ArrayList;

public class Friends {

	ArrayList<BEFriend> m_friends;
	
	public Friends()
	{
		m_friends = new ArrayList<BEFriend>();
		m_friends.add(new BEFriend("Alex", "123456", "alex@gmail.com","alex.com"));
		m_friends.add(new BEFriend("Anders", "234567","Anders@gmail.com","Anders.com"));
		m_friends.add(new BEFriend("Andreas", "126256","Andreas@gmail.com","Andreas.com"));
		m_friends.add(new BEFriend("Asvør", "234567","Asvør@gmail.com","Asvør.com"));
		m_friends.add(new BEFriend("Casper", "123456","Casper@gmail.com","Casper.com"));
		m_friends.add(new BEFriend("Christian", "994567","Christian@gmail.com","Christian.com"));
		m_friends.add(new BEFriend("Szymon", "+4591105297","sz.soczewka@gmail.com","https://www.linkedin.com/in/szymon-soczewka-103062172/"));

	}
	
	public ArrayList<BEFriend> getAll()
	{ return m_friends; }
	
	public String[] getNames()
	{
		String[] res = new String[m_friends.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = m_friends.get(i).getName();
		}
		return res;
	}

}
