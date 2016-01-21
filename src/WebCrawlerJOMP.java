import jomp.runtime.*;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.io.*;

import org.jsoup.Jsoup;

public class WebCrawlerJOMP {

    public static void main(String[] args) throws Exception {
         boolean word_exists = false;
         boolean parent_site = true;
         boolean valid_site = true;
         String validLink = "";
         HashSet crawledList = new HashSet();
         ArrayList links = new ArrayList();
         ArrayList tmplinks = new ArrayList();
         ArrayList tmp_links = new ArrayList();
         ArrayList links_to_add = new ArrayList();
         String txt = null;
         String title = null;
         int level = 1;
         Date start_date = new Date();
         int cores = Runtime.getRuntime().availableProcessors();
         OMP.setNumThreads(cores);
         int tmpLinksCounter = 0;
         
    	// Define starting url, depth and whether
        // to limit crawling in specified host or not
    	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    	String urlstr = null;
    	IOException e = null;
		do {	
    		System.out.println("Please type a valid starting URL");
    		try {
				urlstr = stdIn.readLine().toLowerCase();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.valid_site = valid_site;
  __omp_Object0.e = e;
  __omp_Object0.urlstr = urlstr;
  __omp_Object0.stdIn = stdIn;
  __omp_Object0.tmpLinksCounter = tmpLinksCounter;
  __omp_Object0.cores = cores;
  __omp_Object0.start_date = start_date;
  __omp_Object0.level = level;
  __omp_Object0.title = title;
  __omp_Object0.txt = txt;
  __omp_Object0.links_to_add = links_to_add;
  __omp_Object0.tmp_links = tmp_links;
  __omp_Object0.tmplinks = tmplinks;
  __omp_Object0.links = links;
  __omp_Object0.crawledList = crawledList;
  __omp_Object0.validLink = validLink;
  __omp_Object0.parent_site = parent_site;
  __omp_Object0.word_exists = word_exists;
  __omp_Object0.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  valid_site = __omp_Object0.valid_site;
  e = __omp_Object0.e;
  urlstr = __omp_Object0.urlstr;
  stdIn = __omp_Object0.stdIn;
  tmpLinksCounter = __omp_Object0.tmpLinksCounter;
  cores = __omp_Object0.cores;
  start_date = __omp_Object0.start_date;
  level = __omp_Object0.level;
  title = __omp_Object0.title;
  txt = __omp_Object0.txt;
  links_to_add = __omp_Object0.links_to_add;
  tmp_links = __omp_Object0.tmp_links;
  tmplinks = __omp_Object0.tmplinks;
  links = __omp_Object0.links;
  crawledList = __omp_Object0.crawledList;
  validLink = __omp_Object0.validLink;
  parent_site = __omp_Object0.parent_site;
  word_exists = __omp_Object0.word_exists;
  args = __omp_Object0.args;
}
// OMP PARALLEL BLOCK ENDS

			
    	} while (!valid_site);
		System.out.println("Please select depth");
		int depth = 1;
		try {
			depth = Integer.valueOf(stdIn.readLine());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String lim = null;
		boolean limit = true;
		System.out.println("Limit crawling in this host? [Y/n]");
		try {
			lim = stdIn.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// In case of wrong input limit takes its default value (true)
		if (lim.equalsIgnoreCase("y"))
			limit = true;
		else if ((lim.equalsIgnoreCase("n")))
			limit = false;
		
		stdIn.close();
        URL url = new URL(urlstr); // example: http://www.uom.gr/index.php
        
        /**
         * TODO Set DB Server IP address, create database 'webcrawler', and set proper credentials.
         * DB Schema:
         * 1. Table: urls, Columns: id, url, title
         * 2. Table: wordfreq, Columns: id, word, url, freq
         */
        
        String db = "jdbc:mysql://127.0.0.1:3306/webcrawler?useUnicode=true&characterEncoding=UTF-8";
		String username = "[YOUR_USERNAME_HERE]";
		String password = "[YOUR_PASSWORD_HERE]";
		Connection con = DriverManager.getConnection(db, username, password);
		
		//Retrieve sites already crawled from the database
		Statement st = con.createStatement();
		String query = "SELECT url FROM urls";
		ResultSet rs = st.executeQuery(query);	
		while (rs.next()) {
			crawledList.add(rs.getString(1));
		}
		// Retrieve links
		System.out.print("\nPlease wait while links from " + urlstr + " are being retrieved... ");
        String body = Jsoup.parse(url, 60000).body().toString();
    	tmplinks = RetrieveLinks.retrieveLinks(body);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class2 __omp_Object2 = new __omp_Class2();
  // shared variables
  __omp_Object2.links = links;
  __omp_Object2.body = body;
  __omp_Object2.rs = rs;
  __omp_Object2.query = query;
  __omp_Object2.st = st;
  __omp_Object2.con = con;
  __omp_Object2.password = password;
  __omp_Object2.username = username;
  __omp_Object2.db = db;
  __omp_Object2.url = url;
  __omp_Object2.e = e;
  __omp_Object2.limit = limit;
  __omp_Object2.lim = lim;
  __omp_Object2.depth = depth;
  __omp_Object2.urlstr = urlstr;
  __omp_Object2.stdIn = stdIn;
  __omp_Object2.tmpLinksCounter = tmpLinksCounter;
  __omp_Object2.cores = cores;
  __omp_Object2.start_date = start_date;
  __omp_Object2.level = level;
  __omp_Object2.title = title;
  __omp_Object2.txt = txt;
  __omp_Object2.links_to_add = links_to_add;
  __omp_Object2.tmp_links = tmp_links;
  __omp_Object2.tmplinks = tmplinks;
  __omp_Object2.crawledList = crawledList;
  __omp_Object2.validLink = validLink;
  __omp_Object2.valid_site = valid_site;
  __omp_Object2.parent_site = parent_site;
  __omp_Object2.word_exists = word_exists;
  __omp_Object2.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object2);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  links = __omp_Object2.links;
  body = __omp_Object2.body;
  rs = __omp_Object2.rs;
  query = __omp_Object2.query;
  st = __omp_Object2.st;
  con = __omp_Object2.con;
  password = __omp_Object2.password;
  username = __omp_Object2.username;
  db = __omp_Object2.db;
  url = __omp_Object2.url;
  e = __omp_Object2.e;
  limit = __omp_Object2.limit;
  lim = __omp_Object2.lim;
  depth = __omp_Object2.depth;
  urlstr = __omp_Object2.urlstr;
  stdIn = __omp_Object2.stdIn;
  tmpLinksCounter = __omp_Object2.tmpLinksCounter;
  cores = __omp_Object2.cores;
  start_date = __omp_Object2.start_date;
  level = __omp_Object2.level;
  title = __omp_Object2.title;
  txt = __omp_Object2.txt;
  links_to_add = __omp_Object2.links_to_add;
  tmp_links = __omp_Object2.tmp_links;
  tmplinks = __omp_Object2.tmplinks;
  crawledList = __omp_Object2.crawledList;
  validLink = __omp_Object2.validLink;
  valid_site = __omp_Object2.valid_site;
  parent_site = __omp_Object2.parent_site;
  word_exists = __omp_Object2.word_exists;
  args = __omp_Object2.args;
}
// OMP PARALLEL BLOCK ENDS

    	tmplinks.clear();
    	
       	int sites_to_crawl = links.size()+1;
    	// if starting url has already been crawled, try to start with the first link
    	if (crawledList.contains(url.toString())) {
    		links.remove(url.toString());
	    	if (!links.isEmpty()){
    			url = new URL(links.get(0).toString());
    			sites_to_crawl--;
    		}	
    	}
    	System.out.print(sites_to_crawl + " sites to crawl");
    	
    	while (depth > 0){
    		// While there are more links search them one by one
    		while (!links.isEmpty()){
    			// Retrieve site 's title
    			title = Jsoup.parse(url, 60000).title();
        	
    			System.out.println("\nCrawling... " + title + " (" + url.toString() + ")");
        
    			if (title.contains("'")) {
    				title = title.replaceAll("'", "\\\\\'");
    			}
    			if (title.contains("\"")) {
    				title = title.replaceAll("\"", "\\\\\"");
    			}
    			// Insert site 's url and title to the database
    			st.execute("INSERT INTO urls VALUES (NULL, '" + url + "', '" + title + "')");
    			
    			// Retrieve site 's body
    			body = Jsoup.parse(url, 60000).body().toString();
    		
    			// Retrieve plain text from site 's body (clear html tags)
    			txt = Jsoup.parse(body).text();
    					
				// Split text to words wherever -, / or whitespace (\s) is present
    			String reg_split = "(-|/|\\s)";
    			String[] words = txt.split(reg_split);
    			
    			// Set regex to: Any of English or Greek characters
    			// followed by one of these characters: !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
    			String regex = "(((\\p{L})|(\\p{InGreek}))+(\\p{Punct}){1})";
    			
    			String word;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class6 __omp_Object6 = new __omp_Class6();
  // shared variables
  __omp_Object6.regex = regex;
  __omp_Object6.words = words;
  __omp_Object6.reg_split = reg_split;
  __omp_Object6.sites_to_crawl = sites_to_crawl;
  __omp_Object6.body = body;
  __omp_Object6.rs = rs;
  __omp_Object6.query = query;
  __omp_Object6.st = st;
  __omp_Object6.con = con;
  __omp_Object6.password = password;
  __omp_Object6.username = username;
  __omp_Object6.db = db;
  __omp_Object6.url = url;
  __omp_Object6.e = e;
  __omp_Object6.limit = limit;
  __omp_Object6.lim = lim;
  __omp_Object6.depth = depth;
  __omp_Object6.urlstr = urlstr;
  __omp_Object6.stdIn = stdIn;
  __omp_Object6.tmpLinksCounter = tmpLinksCounter;
  __omp_Object6.cores = cores;
  __omp_Object6.start_date = start_date;
  __omp_Object6.level = level;
  __omp_Object6.title = title;
  __omp_Object6.txt = txt;
  __omp_Object6.links_to_add = links_to_add;
  __omp_Object6.tmp_links = tmp_links;
  __omp_Object6.tmplinks = tmplinks;
  __omp_Object6.links = links;
  __omp_Object6.crawledList = crawledList;
  __omp_Object6.validLink = validLink;
  __omp_Object6.valid_site = valid_site;
  __omp_Object6.parent_site = parent_site;
  __omp_Object6.word_exists = word_exists;
  __omp_Object6.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object6);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  word = __omp_Object6.word;
  regex = __omp_Object6.regex;
  words = __omp_Object6.words;
  reg_split = __omp_Object6.reg_split;
  sites_to_crawl = __omp_Object6.sites_to_crawl;
  body = __omp_Object6.body;
  rs = __omp_Object6.rs;
  query = __omp_Object6.query;
  st = __omp_Object6.st;
  con = __omp_Object6.con;
  password = __omp_Object6.password;
  username = __omp_Object6.username;
  db = __omp_Object6.db;
  url = __omp_Object6.url;
  e = __omp_Object6.e;
  limit = __omp_Object6.limit;
  lim = __omp_Object6.lim;
  depth = __omp_Object6.depth;
  urlstr = __omp_Object6.urlstr;
  stdIn = __omp_Object6.stdIn;
  tmpLinksCounter = __omp_Object6.tmpLinksCounter;
  cores = __omp_Object6.cores;
  start_date = __omp_Object6.start_date;
  level = __omp_Object6.level;
  title = __omp_Object6.title;
  txt = __omp_Object6.txt;
  links_to_add = __omp_Object6.links_to_add;
  tmp_links = __omp_Object6.tmp_links;
  tmplinks = __omp_Object6.tmplinks;
  links = __omp_Object6.links;
  crawledList = __omp_Object6.crawledList;
  validLink = __omp_Object6.validLink;
  valid_site = __omp_Object6.valid_site;
  parent_site = __omp_Object6.parent_site;
  word_exists = __omp_Object6.word_exists;
  args = __omp_Object6.args;
}
// OMP PARALLEL BLOCK ENDS

    			System.out.println(title + " (" + url.toString() + ") Successfully crawled!");
    			
    			// add starting site to crawled list, remove from links (if it existed)
    			// and copy links to tmp_links (for next level of depth)
    			if (parent_site) {
    				crawledList.add(url.toString());
    				for (int i = 0; i < links.size(); i++) {
    					if (links.get(i).toString().equalsIgnoreCase(url.toString()))
    						links.remove(i);
    				}
    				tmp_links.addAll(links);
    				parent_site = false;
    			}
    			// add site just crawled to crawled list and
    			// remove from remaining links to crawl
    			else {
    				crawledList.add(url.toString());
    				links.remove(url.toString());
    			}
    			
    			// check if next link is valid. If not delete it, pick the next one etc.
    			if (!links.isEmpty()) {
    				url = new URL(links.get(0).toString());
    			}
    		}
    		depth--;
    		if (depth > 0) {
    			System.out.println("\nLevel " + level + " crawling completed.\nStarting Level " + ++level + "...");
    			links.clear();
    			
    			// Search by width
    			// For every one of the sites just crawled retrieve its links and continue
    			System.out.print("\nPlease wait while links for level " + level + " crawling are being retreived... ");
    			for (int i = 0; i < tmp_links.size(); i++) {
    				url = new URL(tmp_links.get(i).toString());
    				body = Jsoup.parse(url, 60000).body().toString();
    				tmplinks.clear();
    				tmplinks = RetrieveLinks.retrieveLinks(body);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class10 __omp_Object10 = new __omp_Class10();
  // shared variables
  __omp_Object10.links = links;
  __omp_Object10.i = i;
  __omp_Object10.sites_to_crawl = sites_to_crawl;
  __omp_Object10.body = body;
  __omp_Object10.rs = rs;
  __omp_Object10.query = query;
  __omp_Object10.st = st;
  __omp_Object10.con = con;
  __omp_Object10.password = password;
  __omp_Object10.username = username;
  __omp_Object10.db = db;
  __omp_Object10.url = url;
  __omp_Object10.e = e;
  __omp_Object10.limit = limit;
  __omp_Object10.lim = lim;
  __omp_Object10.depth = depth;
  __omp_Object10.urlstr = urlstr;
  __omp_Object10.stdIn = stdIn;
  __omp_Object10.tmpLinksCounter = tmpLinksCounter;
  __omp_Object10.cores = cores;
  __omp_Object10.start_date = start_date;
  __omp_Object10.level = level;
  __omp_Object10.title = title;
  __omp_Object10.txt = txt;
  __omp_Object10.links_to_add = links_to_add;
  __omp_Object10.tmp_links = tmp_links;
  __omp_Object10.tmplinks = tmplinks;
  __omp_Object10.crawledList = crawledList;
  __omp_Object10.validLink = validLink;
  __omp_Object10.valid_site = valid_site;
  __omp_Object10.parent_site = parent_site;
  __omp_Object10.word_exists = word_exists;
  __omp_Object10.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object10);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  links = __omp_Object10.links;
  i = __omp_Object10.i;
  sites_to_crawl = __omp_Object10.sites_to_crawl;
  body = __omp_Object10.body;
  rs = __omp_Object10.rs;
  query = __omp_Object10.query;
  st = __omp_Object10.st;
  con = __omp_Object10.con;
  password = __omp_Object10.password;
  username = __omp_Object10.username;
  db = __omp_Object10.db;
  url = __omp_Object10.url;
  e = __omp_Object10.e;
  limit = __omp_Object10.limit;
  lim = __omp_Object10.lim;
  depth = __omp_Object10.depth;
  urlstr = __omp_Object10.urlstr;
  stdIn = __omp_Object10.stdIn;
  tmpLinksCounter = __omp_Object10.tmpLinksCounter;
  cores = __omp_Object10.cores;
  start_date = __omp_Object10.start_date;
  level = __omp_Object10.level;
  title = __omp_Object10.title;
  txt = __omp_Object10.txt;
  links_to_add = __omp_Object10.links_to_add;
  tmp_links = __omp_Object10.tmp_links;
  tmplinks = __omp_Object10.tmplinks;
  crawledList = __omp_Object10.crawledList;
  validLink = __omp_Object10.validLink;
  valid_site = __omp_Object10.valid_site;
  parent_site = __omp_Object10.parent_site;
  word_exists = __omp_Object10.word_exists;
  args = __omp_Object10.args;
}
// OMP PARALLEL BLOCK ENDS

    			}	
    			tmp_links.clear();
    			tmp_links.addAll(links);
    			System.out.print(links.size() + " sites to crawl");
    			if (!links.isEmpty())
    				url = new URL(links.get(0).toString());
    		}
    	}
    	st.close();
		con.close();
    	Date finish_date = new Date();
    	System.out.println("\nStart Date: " + start_date);
    	System.out.println("Finish Date: " + finish_date);
    }

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class10 extends jomp.runtime.BusyTask {
  // shared variables
  ArrayList links;
  int i;
  int sites_to_crawl;
  String body;
  ResultSet rs;
  String query;
  Statement st;
  Connection con;
  String password;
  String username;
  String db;
  URL url;
  IOException e;
  boolean limit;
  String lim;
  int depth;
  String urlstr;
  BufferedReader stdIn;
  int tmpLinksCounter;
  int cores;
  Date start_date;
  int level;
  String title;
  String txt;
  ArrayList links_to_add;
  ArrayList tmp_links;
  ArrayList tmplinks;
  HashSet crawledList;
  String validLink;
  boolean valid_site;
  boolean parent_site;
  boolean word_exists;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

    				{
                                         { // OMP FOR BLOCK BEGINS
                                         // copy of firstprivate variables, initialized
                                         // copy of lastprivate variables
                                         // variables to hold result of reduction
                                         boolean amLast=false;
                                         {
                                           // firstprivate variables + init
                                           // [last]private variables
                                           // reduction variables + init to default
                                           // -------------------------------------
                                           jomp.runtime.LoopData __omp_WholeData12 = new jomp.runtime.LoopData();
                                           jomp.runtime.LoopData __omp_ChunkData11 = new jomp.runtime.LoopData();
                                           __omp_WholeData12.start = (long)( 0);
                                           __omp_WholeData12.stop = (long)( tmplinks.size());
                                           __omp_WholeData12.step = (long)(1);
                                           __omp_WholeData12.chunkSize = (long)( 4);
                                           jomp.runtime.OMP.initTicket(__omp_me, __omp_WholeData12);
                                           while(!__omp_ChunkData11.isLast && jomp.runtime.OMP.getLoopDynamic(__omp_me, __omp_WholeData12, __omp_ChunkData11)) {
                                             for(int j = (int)__omp_ChunkData11.start; j < __omp_ChunkData11.stop; j += __omp_ChunkData11.step) {
                                               // OMP USER CODE BEGINS
 {
    						validLink = RetrieveLinks.validateLink(url, tmplinks.get(j).toString(), links, crawledList, limit);
                                                 // OMP CRITICAL BLOCK BEGINS
                                                 synchronized (jomp.runtime.OMP.getLockByName("")) {
                                                 // OMP USER CODE BEGINS

    						{
    							if(!validLink.isEmpty())
    								links.add(validLink);
    						}
                                                 // OMP USER CODE ENDS
                                                 }
                                                 // OMP CRITICAL BLOCK ENDS

    					}
                                               // OMP USER CODE ENDS
                                               if (j == (__omp_WholeData12.stop-1)) amLast = true;
                                             } // of for 
                                           } // of while
                                           // call reducer
                                           jomp.runtime.OMP.resetTicket(__omp_me);
                                           jomp.runtime.OMP.doBarrier(__omp_me);
                                           // copy lastprivate variables out
                                           if (amLast) {
                                           }
                                         }
                                         // set global from lastprivate variables
                                         if (amLast) {
                                         }
                                         // set global from reduction variables
                                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                                         }
                                         } // OMP FOR BLOCK ENDS

    				}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class6 extends jomp.runtime.BusyTask {
  // shared variables
  String word;
  String regex;
  String [ ] words;
  String reg_split;
  int sites_to_crawl;
  String body;
  ResultSet rs;
  String query;
  Statement st;
  Connection con;
  String password;
  String username;
  String db;
  URL url;
  IOException e;
  boolean limit;
  String lim;
  int depth;
  String urlstr;
  BufferedReader stdIn;
  int tmpLinksCounter;
  int cores;
  Date start_date;
  int level;
  String title;
  String txt;
  ArrayList links_to_add;
  ArrayList tmp_links;
  ArrayList tmplinks;
  ArrayList links;
  HashSet crawledList;
  String validLink;
  boolean valid_site;
  boolean parent_site;
  boolean word_exists;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

    			{
                                 { // OMP FOR BLOCK BEGINS
                                 // copy of firstprivate variables, initialized
                                 // copy of lastprivate variables
                                 // variables to hold result of reduction
                                 boolean amLast=false;
                                 {
                                   // firstprivate variables + init
                                   // [last]private variables
                                   // reduction variables + init to default
                                   // -------------------------------------
                                   jomp.runtime.LoopData __omp_WholeData8 = new jomp.runtime.LoopData();
                                   jomp.runtime.LoopData __omp_ChunkData7 = new jomp.runtime.LoopData();
                                   __omp_WholeData8.start = (long)( 0);
                                   __omp_WholeData8.stop = (long)( words.length);
                                   __omp_WholeData8.step = (long)(1);
                                   __omp_WholeData8.chunkSize = (long)( 4);
                                   jomp.runtime.OMP.initTicket(__omp_me, __omp_WholeData8);
                                   while(!__omp_ChunkData7.isLast && jomp.runtime.OMP.getLoopDynamic(__omp_me, __omp_WholeData8, __omp_ChunkData7)) {
                                     for(int i = (int)__omp_ChunkData7.start; i < __omp_ChunkData7.stop; i += __omp_ChunkData7.step) {
                                       // OMP USER CODE BEGINS

    				{
    					word = words[i];
    					// Check for some symbols or common words
    					// bigger than 3 letters to ignore them
    					if (word.startsWith("("))
    						word = word.substring(1);
        				
    					if (word.endsWith(")"))
    						word = word.substring(0, word.length()-1);
        			
    					if (word.matches(regex))
    						word = word.substring(0, word.length()-1);
    					else if (word.endsWith("'s"))
    						word = word.substring(0, word.length()-2);
    					else if (word.matches("this") || word.matches("that") || word.matches("these") || word.matches("those") ||
    							 word.matches("from") || word.matches("what") || word.matches("where"))
    						word = "";
    					else if (word.contains("'"))
    						word = word.replaceAll("'", "\\\\\'");
    					else if (word.contains("\""))
            				word = word.replaceAll("\"", "\\\\\"");
        				
    					if (!word.matches("(((\\p{L})|(\\p{InGreek}))+)"))
    						word = "";
        			
    					word_exists = false;
    					
    					// Accept words longer than 3 and
    					// up to 40 letters (to prevent faults on sql INSERT)
    					if (word.length() > 3 && word.length() < 40) {
                                         // OMP CRITICAL BLOCK BEGINS
                                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                                         // OMP USER CODE BEGINS

    					{
    						Statement st2 = con.createStatement();
    						query = "SELECT * FROM wordfreq WHERE word = '" + word + "' AND url = '" + url.toString() + "'";
    						rs = st.executeQuery(query);
    						// if word exists in the database, update its frequency
    						while (rs.next()) {
    							st2.execute("UPDATE wordfreq SET freq = freq+1 WHERE word = '" + rs.getString(1) + "' AND url = '" + rs.getString(2) + "'");
    							st2.close();
    							word_exists = true;
    						}
    						// else insert the combination word/url and set its frequency to 1
    						if (!word_exists) {
    							st2.execute("INSERT INTO wordfreq VALUES (NULL, '" + word.toLowerCase() + "', '" + url.toString() + "', 1)");
    							st2.close();
    						}
    					}
                                         // OMP USER CODE ENDS
                                         }
                                         // OMP CRITICAL BLOCK ENDS
	
    					}
    				}
                                       // OMP USER CODE ENDS
                                       if (i == (__omp_WholeData8.stop-1)) amLast = true;
                                     } // of for 
                                   } // of while
                                   // call reducer
                                   jomp.runtime.OMP.resetTicket(__omp_me);
                                   jomp.runtime.OMP.doBarrier(__omp_me);
                                   // copy lastprivate variables out
                                   if (amLast) {
                                   }
                                 }
                                 // set global from lastprivate variables
                                 if (amLast) {
                                 }
                                 // set global from reduction variables
                                 if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                                 }
                                 } // OMP FOR BLOCK ENDS

    			}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class2 extends jomp.runtime.BusyTask {
  // shared variables
  ArrayList links;
  String body;
  ResultSet rs;
  String query;
  Statement st;
  Connection con;
  String password;
  String username;
  String db;
  URL url;
  IOException e;
  boolean limit;
  String lim;
  int depth;
  String urlstr;
  BufferedReader stdIn;
  int tmpLinksCounter;
  int cores;
  Date start_date;
  int level;
  String title;
  String txt;
  ArrayList links_to_add;
  ArrayList tmp_links;
  ArrayList tmplinks;
  HashSet crawledList;
  String validLink;
  boolean valid_site;
  boolean parent_site;
  boolean word_exists;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

    	{
                 { // OMP FOR BLOCK BEGINS
                 // copy of firstprivate variables, initialized
                 // copy of lastprivate variables
                 // variables to hold result of reduction
                 boolean amLast=false;
                 {
                   // firstprivate variables + init
                   // [last]private variables
                   // reduction variables + init to default
                   // -------------------------------------
                   jomp.runtime.LoopData __omp_WholeData4 = new jomp.runtime.LoopData();
                   jomp.runtime.LoopData __omp_ChunkData3 = new jomp.runtime.LoopData();
                   __omp_WholeData4.start = (long)( 0);
                   __omp_WholeData4.stop = (long)( tmplinks.size());
                   __omp_WholeData4.step = (long)(1);
                   __omp_WholeData4.chunkSize = (long)( 4);
                   jomp.runtime.OMP.initTicket(__omp_me, __omp_WholeData4);
                   while(!__omp_ChunkData3.isLast && jomp.runtime.OMP.getLoopDynamic(__omp_me, __omp_WholeData4, __omp_ChunkData3)) {
                     for(int tmpLinksCounter = (int)__omp_ChunkData3.start; tmpLinksCounter < __omp_ChunkData3.stop; tmpLinksCounter += __omp_ChunkData3.step) {
                       // OMP USER CODE BEGINS
 {
    			validLink = RetrieveLinks.validateLink(url, tmplinks.get(tmpLinksCounter).toString(), links, crawledList, limit);
                         // OMP CRITICAL BLOCK BEGINS
                         synchronized (jomp.runtime.OMP.getLockByName("")) {
                         // OMP USER CODE BEGINS

    			{
    				if(!validLink.isEmpty() && !links.contains(validLink))
    					links.add(validLink);
    			}
                         // OMP USER CODE ENDS
                         }
                         // OMP CRITICAL BLOCK ENDS
		
    		}
                       // OMP USER CODE ENDS
                       if (tmpLinksCounter == (__omp_WholeData4.stop-1)) amLast = true;
                     } // of for 
                   } // of while
                   // call reducer
                   jomp.runtime.OMP.resetTicket(__omp_me);
                   jomp.runtime.OMP.doBarrier(__omp_me);
                   // copy lastprivate variables out
                   if (amLast) {
                   }
                 }
                 // set global from lastprivate variables
                 if (amLast) {
                 }
                 // set global from reduction variables
                 if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                 }
                 } // OMP FOR BLOCK ENDS
	
    	}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS



// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  boolean valid_site;
  IOException e;
  String urlstr;
  BufferedReader stdIn;
  int tmpLinksCounter;
  int cores;
  Date start_date;
  int level;
  String title;
  String txt;
  ArrayList links_to_add;
  ArrayList tmp_links;
  ArrayList tmplinks;
  ArrayList links;
  HashSet crawledList;
  String validLink;
  boolean parent_site;
  boolean word_exists;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

    		{
                         { // OMP SECTIONS BLOCK BEGINS
                         // copy of firstprivate variables, initialized
                         // copy of lastprivate variables
                         // variables to hold result of reduction
                         boolean amLast=false;
                         {
                           // firstprivate variables + init
                           // [last]private variables
                           // reduction variables + init to default
                           // -------------------------------------
                           __ompName_1: while(true) {
                           switch((int)jomp.runtime.OMP.getTicket(__omp_me)) {
                           // OMP SECTION BLOCK 0 BEGINS
                             case 0: {
                           // OMP USER CODE BEGINS

    				{
    					if (!RetrieveLinks.validSite(urlstr))
						valid_site = false;
					}
                           // OMP USER CODE ENDS
                             };  break;
                           // OMP SECTION BLOCK 0 ENDS
                           // OMP SECTION BLOCK 1 BEGINS
                             case 1: {
                           // OMP USER CODE BEGINS

    				{
    					if (!RetrieveLinks.validMimeType(urlstr))
						valid_site = false;
					}
                           // OMP USER CODE ENDS
                           amLast = true;
                             };  break;
                           // OMP SECTION BLOCK 1 ENDS

                             default: break __ompName_1;
                           } // of switch
                           } // of while
                           // call reducer
                           jomp.runtime.OMP.resetTicket(__omp_me);
                           jomp.runtime.OMP.doBarrier(__omp_me);
                           // copy lastprivate variables out
                           if (amLast) {
                           }
                         }
                         // update lastprivate variables
                         if (amLast) {
                         }
                         // update reduction variables
                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                         }
                         } // OMP SECTIONS BLOCK ENDS
		
			}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}
