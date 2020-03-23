package dk.easv.friendsv2.Model;

import java.util.ArrayList;

public class Friends {

	ArrayList<BEFriend> m_friends;
	
	public Friends()
	{
		m_friends = new ArrayList<BEFriend>();
		m_friends.add(new BEFriend("Zlex", "123456", "alex@gmail.com","alex.com","Spansbjerg Kirkevej 103, Esbjerg"));
		m_friends.add(new BEFriend("Anders", "234567","Anders@gmail.com","Anders.com","Spansbjerg Kirkevej 103, Esbjerg"));
		m_friends.add(new BEFriend("Andreas", "126256","Andreas@gmail.com","Andreas.com","Spansbjerg Kirkevej 103, Esbjerg"));
		m_friends.add(new BEFriend("Asvør", "234567","Asvør@gmail.com","Asvør.com","Spansbjerg Kirkevej 103, Esbjerg"));
		m_friends.add(new BEFriend("Casper", "123456","Casper@gmail.com","Casper.com","Spansbjerg Kirkevej 103, Esbjerg"));
		m_friends.add(new BEFriend("Christian", "994567","Christian@gmail.com","Christian.com","Spansbjerg Kirkevej 103, Esbjerg"));
		m_friends.add(new BEFriend("Szymon", "+4591105297","sz.soczewka@gmail.com","https://www.linkedin.com/in/szymon-soczewka-103062172/","Spansbjerg Kirkevej 103, Esbjerg"));

	}
	
	public ArrayList<BEFriend> getAll()
	{ return m_friends; }
	public void AddFriend(BEFriend friend){
		m_friends.add(friend);
	}
}
