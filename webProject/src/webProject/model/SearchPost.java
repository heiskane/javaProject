package webProject.model;

import java.util.ArrayList;

import webProject.model.dao.Dao;

public class SearchPost {

	public SearchPost() {
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<UserPost> search(String search) {
		
		Dao dao = new Dao();
		ArrayList<UserPost> posts = dao.listAllPosts();
		ArrayList<UserPost> results = new ArrayList<UserPost>();
		
		for (UserPost post : posts)
		{
			// Search by title, content or user
			// Everything is set to lowercase to make the search case insensitive
			String content = post.getContent().toLowerCase();
			String title = post.getTitle().toLowerCase();
			String user = post.getUser().toLowerCase();
			if (content.contains(search) || title.contains(search) || user.contains(search))
			{
				results.add(post);
			}
		}
		
		return results;
	}

}
